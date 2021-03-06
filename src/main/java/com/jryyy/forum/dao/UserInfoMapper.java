package com.jryyy.forum.dao;

import com.jryyy.forum.model.Check;
import com.jryyy.forum.model.UserInfo;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.model.response.UserInfoResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息Mapper
 *
 * @author JrYYY
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 修改用户名（只能修改一次）
     *
     * @param userId   用户id
     * @param username 用户名
     * @return 修改数量
     */
    @Update("update user_info set username = #{username} where userId = #{userId}")
    int updateUsername(Integer userId, String username);

    /**
     * 清除用户信息
     *
     * @param userId 用户id
     * @throws Exception
     */
    @Delete("delete from user_info where userId = #{userId}")
    void deleteUserInfo(int userId) throws Exception;

    /**
     * 修改积分
     *
     * @param userId   用户id
     * @param credit 积分
     * @return 修改数目
     * @throws Exception
     */
    @Update("update user_info set credit = #{credit} where userId = #{userId}")
    int updateCredit(Integer userId, Integer credit) throws Exception;

    /**
     * 查看用户积分
     *
     * @param userId 用户id
     * @return 积分
     * @throws Exception
     */
    @Select("select credit from user_info where userId = #{userId}")
    int findCreditByUserId(Integer userId) throws Exception;

    /**
     * 更改最近的登入
     *
     * @param dateTime 时间
     * @param userId   用户id
     */
    @Update("update user_info set recentLogin = #{dateTime} where userId = #{userId}")
    void updateRecentLoginDate(LocalDateTime dateTime, Integer userId);

    /**
     * 查询用户信息
     *
     * @param id 用户id
     * @return {@link UserInfo}
     * @throws Exception
     */
    @Select("select userId,email,username,nickname,avatar,sex,age,email," +
            "checkInDays,checkInDate,bio,continuousCheckInDays continuousDays," +
            "bgImg,credit,recentLogin,recentState " +
            "from user_info where userId = #{id}")
    UserInfo selectUserInfo(@Param("id") int id) throws Exception;

    /**
     * 更具条件查询用户信息列表
     *
     * @param value 查询条件
     * @return {@link UserInfoResponse}
     * @throws Exception
     */
    @Select("select userId,username,nickname,email,bio,avatar,recentLogin,recentState " +
            "from user_info where username like concat('%', #{value}, '%') " +
            "or email like concat('%', #{value}, '%') " +
            "or nickname like concat('%', #{value}, '%')")
    List<UserInfoResponse> findInfoByCondition(String value) throws Exception;

    /**
     * 查询用户简约信息
     *
     * @param userId 用id
     * @return {@link UserInfoResponse}
     * @throws Exception
     */
    @Select("select A.userId,A.username,A.nickname,A.email,A.bio,A.avatar,B.status,A.recentLogin,A.recentState " +
            "from user_info A join user B on A.userId = B.id where userId = #{userId}")
    UserInfoResponse findInfoByUserId(Integer userId);

    /**
     * 初始化创建用户信息
     *
     * @param id    用户id
     * @param email 邮箱
     * @param username 用户名称唯一键
     * @throws Exception
     */
    @Insert("insert into user_info(userId,nickname,username,email,checkInDate,recentLogin) " +
            "value (#{id},'无名氏',#{username},#{email},#{checkInDate},now())")
    void insertUserInfo(int id, String email, String username, LocalDate checkInDate) throws Exception;

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
     * 设置背景图片
     *
     * @param userId 用户id
     * @param avatar 用户头像
     * @throws Exception
     */
    @Update("update user_info set avatar = #{avatar} where userId = #{userId}")
    void updateUserAvatar(int userId, String avatar) throws Exception;

    /**
     * 查询签到日期和最近签到数据
     *
     * @param id 用户id
     * @return {@link Check}
     * @throws Exception
     */
    @Select("select checkInDays,checkInDate,continuousCheckInDays continuousDays " +
            "from user_info where userId = #{id}")
    Check selectCheckIn(@Param("id") int id) throws Exception;

    /**
     * 签到
     *
     * @param checkInDays    签到天数
     * @param userId         用户id
     * @param continuousDays 连续签到
     * @throws Exception
     */
    @Update("update user_info set checkInDays = #{checkInDays}," +
            "continuousCheckInDays = #{continuousDays}," +
            "checkInDate = CURRENT_TIMESTAMP where userId = #{userId}")
    void checkIn(@Param("userId") int userId, @Param("checkInDays") int checkInDays,
                 @Param("continuousDays") int continuousDays) throws Exception;

    /**
     * 没有连续签到至0
     *
     * @param userId userId
     * @throws Exception
     */
    @Update("update user_info set continuousCheckInDays = 0 where userId = #{userId}")
    void modifyContinuousCheckIn(int userId) throws Exception;

    /**
     * 更新用户信息
     *
     * @param userInfo {@link UserInfoRequest}
     * @throws Exception
     */
    @UpdateProvider(type = SqlProvider.class, method = "updatePersonSql")
    void updateUserInfo(UserInfoRequest userInfo) throws Exception;

    /**
     * sql构造器构造动态sql
     */
    class SqlProvider {
        public String updatePersonSql(UserInfoRequest userInfo) {
            return new SQL() {{
                UPDATE("user_info");
                if (userInfo.getNickname() != null) {
                    SET("nickname = #{nickname}"); }
                if (userInfo.getSex() != null){
                    SET("sex = #{sex}");}
                if (userInfo.getAge() != null){
                    SET("age = #{age}");}
                if (userInfo.getBio() != null){
                    SET("bio = #{bio}");}
                WHERE("userId = #{userId}");
            }}.toString();
        }
    }
}
