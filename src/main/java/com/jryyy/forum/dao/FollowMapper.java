package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserFriend;
import com.jryyy.forum.models.response.UserFriendResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 关注/被关注 列表操作
 */
@Mapper
public interface FollowMapper {

    /**
     * 粉丝数
     *
     * @param friendId FanId
     * @return FanCount
     * @throws Exception
     */
    @Select("select count(*) from follow where friendId = #{friendId}")
    int followersNumByUId(@Param("friendId") int friendId) throws Exception;

    /**
     * 关注数
     *
     * @param userId 用户id
     * @return 统计数
     * @throws Exception
     */
    @Select("select count(*) from follow where userId = #{userId}")
    int followingTotalByFId(@Param("userId") int userId) throws Exception;

    /**
     * 查询关注列表
     *
     * @param userId 用户id
     * @return {@link UserFriendResponse}
     * @throws Exception
     */
    @Select("select A.id,A.friendId userId,B.username,B.avatar,B.bio,A.createDate date " +
            "from follow A join user_info B " +
            "on A.friendId = B.userId " +
            "where A.userId = #{userId}")
    List<UserFriendResponse> findAttentionBasedOnId(@Param("userId") Integer userId) throws Exception;

    /**
     * 查询粉丝列表
     *
     * @param friendId 用户id
     * @return {@link UserFriendResponse}
     * @throws Exception
     */
    @Select("select A.id,A.userId,B.username,B.avatar,B.bio,A.createDate date " +
            "from follow A join user_info B " +
            "on A.userId = B.userId " +
            "where A.friendId = #{friendId}")
    List<UserFriendResponse> findFansBasedOnId(@Param("friendId") Integer friendId) throws Exception;


    /**
     * 验证
     *
     * @param id 编号
     * @return {@link UserFriend}
     * @throws Exception
     */
    @Select("select id,userId,friendId,createDate from follow where id = #{id}")
    UserFriend findFriendsBasedOnId(@Param("id") int id) throws Exception;

    /**
     * 验证
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return id
     * @throws Exception
     */
    @Select("select id from follow where userId = #{userId} and friendId = #{friendId}")
    Integer findFriend(@Param("userId") int userId, @Param("friendId") int friendId) throws Exception;

    /**
     * 添加好友
     *
     * @param userFriend {@link UserFriend}
     * @throws Exception
     */
    @Insert("insert into follow(userId,friendId)values(#{userId},#{friendId})")
    void insertUserFriend(UserFriend userFriend) throws Exception;

    /**
     * 取消关注
     *
     * @param userId 用户id
     * @param id
     * @throws Exception
     */
    @Delete("delete from follow where userId = #{userId} and id = #{id}")
    void unsubscribe(@Param("userId") int userId, @Param("id") int id) throws Exception;

}