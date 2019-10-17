package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;

public interface UserFriendService {

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
     * @param fanId
     * @return {@link Response}
     * @throws Exception
     */
    Response viewFanList(int fanId) throws Exception;

    /**
     * 关注
     *
     * @param userId 用户id
     * @param email  邮箱
     * @return {@link Response}
     * @throws Exception
     */
    Response attention(int userId, String email) throws Exception;

    /**
     * 取关
     *
     * @param userId 用户id
     * @param id
     * @return {@link Response}
     * @throws Exception
     */
    Response takeOff(int userId, int id) throws Exception;


}
