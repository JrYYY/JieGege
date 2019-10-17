package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserFriend;
import com.jryyy.forum.models.response.UserFriendResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 好友列表操作
 */
@Mapper
public interface UserFriendMapper {


    /**
     * 查询关注列表
     *
     * @param userId 用户id
     * @return {@link UserFriendResponse}
     * @throws Exception
     */
    @Select("select A.id,B.username,B.avatarUrl,A.createDate date " +
            "from user_friend A join userinfo B " +
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
    @Select("select A.id,B.username,B.avatarUrl,A.createDate date " +
            "from user_friend A join userinfo B " +
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
    @Select("select id,userId,friendId,createDate from user_friend where id = #{id}")
    UserFriend findFriendsBasedOnId(@Param("id") int id) throws Exception;

    /**
     * 验证
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return
     * @throws Exception
     */
    @Select("select id from user_friend where userId = #{userId} and friendId = #{friendId}")
    Integer findFriend(@Param("userId") int userId, @Param("friendId") int friendId) throws Exception;

    /**
     * 添加好友
     *
     * @param userFriend {@link UserFriend}
     * @throws Exception
     */
    @Insert("insert into user_friend(userId,friendId,createDate)values(#{userId},#{friendId},#{createDate})")
    void insertUserFriend(UserFriend userFriend) throws Exception;


    /**
     * 取消关注
     *
     * @param userId 用户id
     * @param id
     * @throws Exception
     */
    @Delete("delete from user_friend where userId = #{userId} and id = #{id}")
    void unsubscribe(@Param("userId") int userId, @Param("id") int id) throws Exception;

}
