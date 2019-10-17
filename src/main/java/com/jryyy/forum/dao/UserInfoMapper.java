package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserInfo;
import com.jryyy.forum.models.request.UserInfoRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
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
    @Select("select userId,username,avatarUrl,sex,age where userId = #{id}")
    UserInfo selectUserInfo(int id) throws Exception;

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

    /*
    sql构造器构造动态sql
     */
    class SqlProvider {
        public String updatePersonSql(UserInfoRequest userInfo) {
            return new SQL() {{
                UPDATE("userinfo");
                if (userInfo.getUsername() != null)
                    SET("username = #{username}");
                if (userInfo.getAvatarUrl() != null)
                    SET("avatarUrl = #{avatarUrl}");
                if (userInfo.getSex() != null)
                    SET("sex = #{sex}");
                if (userInfo.getAge() != null)
                    SET("age = #{age}");
                WHERE("userId = #{userId}");
            }}.toString();
        }
    }
}
