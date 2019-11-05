package com.jryyy.forum.dao;

import com.jryyy.forum.models.Check;
import com.jryyy.forum.models.UserInfo;
import com.jryyy.forum.models.request.UserInfoRequest;
import com.jryyy.forum.models.response.InfoListResponse;
import com.jryyy.forum.models.response.UserInfoResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/*
    用户信息Mapper
 */
@Mapper
public interface UserInfoMapper {

    @Delete("delete from userinfo where userId = #{userId}")
    void deleteUserInfo(int userId) throws Exception;

    /**
     * 查询用户信息
     *
     * @param id 用户id
     * @return {@link UserInfo}
     * @throws Exception
     */
    @Select("select A.userId,B.emailName email,A.username,A.avatar,A.sex,A.age," +
            "A.checkInDays,A.checkInDate,A.bio," +
            "A.continuousCheckInDays continuousDays,A.bgImg " +
            "from userinfo A join user B on A.userId = B.id where A.userId = #{id}")
    UserInfoResponse selectUserInfo(@Param("id") int id) throws Exception;

    /**
     * 更具条件查询用户信息列表
     *
     * @param value 查询条件
     * @return {@link InfoListResponse}
     * @throws Exception
     */
    @Select("select A.userId,A.username,B.emailName email,A.bio from userinfo A " +
            "join user B on A.userId = B.id " +
            "where A.username like '%${value}%' or B.emailName like '%${value}%' ")
    List<InfoListResponse> findInfoByCondition(String value) throws Exception;

    /**
     * 查询签到日期和最近签到数据
     *
     * @param id 用户id
     * @return {@link Check}
     * @throws Exception
     */
    @Select("select checkInDays,checkInDate,continuousCheckInDays continuousDays from userinfo where userId = #{id}")
    Check selectCheckIn(@Param("id") int id) throws Exception;

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
    void updateUserInfo(UserInfoRequest userInfo) throws Exception;

    /**
     * 设置背景图片
     *
     * @param userId 用户id
     * @param bgImg  背景图片
     * @throws Exception
     */
    @Update("update userinfo set bgImg = #{bgImg} where userId = #{userId}")
    void updateUserBgImg(int userId, String bgImg) throws Exception;

    /**
     * 签到
     *
     * @param checkInDays 签到天数
     * @param userId      用户id
     */
    @Update("update userinfo set checkInDays = #{checkInDays}," +
            "continuousCheckInDays = #{continuousDays}," +
            "checkInDate = CURRENT_TIMESTAMP where userId = #{userId}")
    void checkIn(@Param("userId") int userId,
                 @Param("checkInDays") int checkInDays,
                 @Param("continuousDays") int continuousDays) throws Exception;

    /**
     * 没有连续签到至0
     *
     * @param userId
     * @throws Exception
     */
    @Update("update userinfo set continuousCheckInDays = 0 where userId = #{userId}")
    void modifyContinuousCheckIn(int userId) throws Exception;

    /**
     * 删除
     * @param userId    用户id
     * @throws Exception
     */
    @Update("update userinfo set checkInDate = null where userId =#{userId}")
    void deleteCheckInDate(@Param("userId") int userId) throws Exception;

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
                if (userInfo.getBio() != null)
                    SET("bio = #{bio}");
                WHERE("userId = #{userId}");
            }}.toString();
        }
    }
}
