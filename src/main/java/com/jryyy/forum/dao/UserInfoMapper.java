package com.jryyy.forum.dao;

import com.jryyy.forum.model.Check;
import com.jryyy.forum.model.UserInfo;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.model.response.InfoListResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 用户信息Mapper
 * @author JrYYY
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 清除用户信息
     * @param userId    用户id
     * @throws Exception
     */
    @Delete("delete from user_info where userId = #{userId}")
    void deleteUserInfo(int userId) throws Exception;

    /**
     * 查询用户信息
     *
     * @param id 用户id
     * @return {@link UserInfo}
     * @throws Exception
     */
    @Select("select userId,email,username,avatar,sex,age," +
            "checkInDays,checkInDate,bio," +
            "continuousCheckInDays continuousDays,bgImg " +
            "from user_info where userId = #{id}")
    UserInfo selectUserInfo(@Param("id") int id) throws Exception;

    /**
     * 更具条件查询用户信息列表
     *
     * @param value 查询条件
     * @return {@link InfoListResponse}
     * @throws Exception
     */
    @Select("select userId,username,email,bio,avatar from user_info " +
            "where username like concat('%', #{value}, '%') " +
            "or email like concat('%', #{value}, '%')" +
            "or userId like concat('%', #{value}, '%')")
    List<InfoListResponse> findInfoByCondition(String value) throws Exception;

    /**
     * 查询签到日期和最近签到数据
     *
     * @param id 用户id
     * @return {@link Check}
     * @throws Exception
     */
    @Select("select checkInDays,checkInDate,continuousCheckInDays continuousDays from user_info where userId = #{id}")
    Check selectCheckIn(@Param("id") int id) throws Exception;

    /**
     * 初始化创建用户信息
     *
     * @param id    用户id
     * @param email 邮箱
     * @throws Exception
     */
    @Insert("insert into user_info(userId,email) value (#{id},#{email})")
    void insertUserInfo(int id,String email) throws Exception;

    /**
     * 设置背景图片
     *
     * @param userId 用户id
     * @param bgImg  背景图片
     * @throws Exception
     */
    @Update("update user_info set bgImg = #{bgImg} where userId = #{userId}")
    void updateUserBgImg(int userId, String bgImg) throws Exception;

    /**
     * 签到
     *
     * @param checkInDays 签到天数
     * @param userId      用户id
     */
    @Update("update user_info set checkInDays = #{checkInDays}," +
            "continuousCheckInDays = #{continuousDays}," +
            "checkInDate = CURRENT_TIMESTAMP where userId = #{userId}")
    void checkIn(@Param("userId") int userId,
                 @Param("checkInDays") int checkInDays,
                 @Param("continuousDays") int continuousDays) throws Exception;

    /**
     * 没有连续签到至0
     * @param userId userId
     * @throws Exception
     */
    @Update("update user_info set continuousCheckInDays = 0 where userId = #{userId}")
    void modifyContinuousCheckIn(int userId) throws Exception;

    /**
     * 删除
     *
     * @param userId 用户id
     * @throws Exception
     */
    @Update("update user_info set checkInDate = null where userId =#{userId}")
    void deleteCheckInDate(@Param("userId") int userId) throws Exception;


    /**
     * 更新用户信息
     * @param userInfo {@link UserInfoRequest}
     */
    @UpdateProvider(type = SqlProvider.class, method = "updatePersonSql")
    int updateUserInfo(UserInfoRequest userInfo) throws Exception;


    /** sql构造器构造动态sql */
    class SqlProvider {
        public String updatePersonSql(UserInfoRequest userInfo) {
            return new SQL() {{
                UPDATE("user_info");
                if (userInfo.getUsername() != null)
                    SET("username = #{username}");
                if (userInfo.getSex() != null)
                    SET("sex = #{sex}");
                if (userInfo.getAge() != null)
                    SET("age = #{age}");
                if (userInfo.getBio() != null)
                    SET("bio = #{bio}");
                WHERE("userId = #{userId}");
            }}.toString();
        }
    }
}
