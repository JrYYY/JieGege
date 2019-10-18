package com.jryyy.forum.dao;

import com.jryyy.forum.models.CheckIn;
import com.jryyy.forum.models.UserInfo;
import com.jryyy.forum.models.request.UserInfoRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

/*
    用户信息Mapper
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 查询用户信息
     *
     * @param id 用户id
     * @return {@link UserInfo}
     * @throws Exception
     */
    @Select("select username,avatar,sex,age,checkInDays,checkInDate from userinfo where userId = #{id}")
    UserInfo selectUserInfo(@Param("id") int id) throws Exception;

    /**
     * 查询签到日期和最近签到数据
     *
     * @param id 用户id
     * @return {@link CheckIn}
     * @throws Exception
     */
    @Select("select checkInDays,checkInDate from userinfo where userId = #{id}")
    CheckIn selectCheckIn(@Param("id") int id) throws Exception;

    /**
     * 初始化创建用户信息
     *
     * @param id 用户id
     * @throws Exception
     */
    @Insert("insert into userinfo(userId) value (#{id})")
    void insertUserInfo(int id) throws Exception;

    /**
     * 添加用户信息
     *
     * @param userInfo {@link UserInfoRequest}
     */
    @UpdateProvider(type = SqlProvider.class, method = "updatePersonSql")
    void updateUserInfo(UserInfoRequest userInfo);

    /**
     * 签到
     *
     * @param checkInDays 签到天数
     * @param userId      用户id
     */
    @Update("update userinfo set checkInDays = #{checkInDays}," +
            "checkInDate = CURRENT_TIMESTAMP where userId = #{userId}")
    void checkIn(@Param("userId") int userId, @Param("checkInDays") int checkInDays);


    /*
    sql构造器构造动态sql
     */
    class SqlProvider {
        public String updatePersonSql(UserInfoRequest userInfo) {
            return new SQL() {{
                UPDATE("userinfo");
                if (userInfo.getUsername() != null)
                    SET("username = #{username}");
                if (userInfo.getAvatar() != null)
                    SET("avatar = #{avatar}");
                if (userInfo.getSex() != null)
                    SET("sex = #{sex}");
                if (userInfo.getAge() != null)
                    SET("age = #{age}");
                WHERE("userId = #{userId}");
            }}.toString();
        }
    }
}
