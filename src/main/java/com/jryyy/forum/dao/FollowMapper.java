package com.jryyy.forum.dao;

import com.jryyy.forum.model.UserFollow;
import com.jryyy.forum.model.response.FollowResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 关注/被关注 列表操作
 * @author OU
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
    int followersQuantityByUserId(@Param("friendId") int friendId) throws Exception;

    /**
     * 关注数
     *
     * @param userId 用户id
     * @return 统计数
     * @throws Exception
     */
    @Select("select count(*) from follow where userId = #{userId}")
    int followingQuantityByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 查询关注列表
     *
     * @param userId 用户id
     * @return {@link FollowResponse}
     * @throws Exception
     */
    @Select("select A.id,A.friendId userId,B.username,B.avatar,B.bio,A.createDate date " +
            "from follow A join user_info B " +
            "on A.friendId = B.userId " +
            "where A.userId = #{userId}")
    List<FollowResponse> findAttentionBasedOnId(@Param("userId") Integer userId) throws Exception;

    /**
     * 查询粉丝列表
     *
     * @param friendId 用户id
     * @return {@link FollowResponse}
     * @throws Exception
     */
    @Select("select A.id,A.userId,B.username,B.avatar,B.bio,A.createDate date " +
            "from follow A join user_info B " +
            "on A.userId = B.userId " +
            "where A.friendId = #{friendId}")
    List<FollowResponse> findFansBasedOnId(@Param("friendId") Integer friendId) throws Exception;


    /**
     * 验证
     *
     * @param id 编号
     * @return {@link UserFollow}
     * @throws Exception
     */
    @Select("select id,userId,friendId,createDate from follow where id = #{id}")
    UserFollow findFriendsBasedOnId(@Param("id") int id) throws Exception;

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
     * @param userFollow {@link UserFollow}
     * @throws Exception
     */
    @Insert("insert into follow(userId,friendId)values(#{userId},#{friendId})")
    void insertUserFriend(UserFollow userFollow) throws Exception;

    /**
     * 取消关注
     *
     * @param userId 用户id
     * @param id id
     * @throws Exception
     */
    @Delete("delete from follow where userId = #{userId} and id = #{id}")
    void unsubscribe(@Param("userId") int userId, @Param("id") int id) throws Exception;

}
