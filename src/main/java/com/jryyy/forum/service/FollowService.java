package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 追随者/追随 服务
 * @author JrYYY
 */
public interface FollowService {

    /**
     * 查看关注列表
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response viewWatchlist(int userId) throws Exception;

    /**
     * 查看粉丝列表
     *
     * @param fanId 粉丝id
     * @return {@link Response}
     * @throws Exception
     */
    Response viewFanList(int fanId) throws Exception;

    /**
     * 关注
     *
     * @param userId 用户id
     * @param id 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response attention(int userId, int id) throws Exception;

    /**
     * 取关
     *
     * @param userId 用户id
     * @param id    id
     * @return {@link Response}
     * @throws Exception
     */
    Response takeOff(int userId, int id) throws Exception;

    /**
     * 判断已关注
     *
     * @param userId 用户id
     * @param email  邮箱
     * @return true/false
     * @throws Exception
     */
    Response judgedHasBeenConcerned(int userId, String email) throws Exception;

//    /**
//     * 粉丝数
//     * @return  {@link Response}
//     * @throws Exception
//     */
//    public Response followersNumber(Integer userId) throws Exception;

//    /**
//     * 关注数
//     * @return  {@link Response}
//     * @throws Exception
//     */
//    Response followingNumber(Integer fanId)throws Exception;
}
