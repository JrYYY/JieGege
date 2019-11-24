package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;

/**
 * 管理员服务
 */
public interface AdminService {

    /**
     * 查询所有用户
     *
     * @return {@link com.jryyy.forum.models.response.AdminFindUserResponse}
     */
    Response findAllUsers() throws Exception;

    /**
     * 注销用户
     *
     * @param userId id
     * @return {@link Response}
     */
    Response deleteUser(int userId) throws Exception;

    /**
     * @param id 用户id
     * @return {@link Response}
     */
    Response unlock(int id) throws Exception;

    /**
     * @param id  用户
     * @param day 天数
     * @return {@link Response}
     */
    Response lock(int id, int day) throws Exception;

}
