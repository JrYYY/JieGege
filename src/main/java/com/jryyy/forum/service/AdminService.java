package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 管理员服务
 * @author OU
 */
public interface AdminService {

    /**
     * 查询所有用户
     *
     * @return {@link com.jryyy.forum.model.response.AdminFindUserResponse}
     * @throws Exception
     */
    Response findAllUsers() throws Exception;

    /**
     * 查询所有评论
     *
     * @return {@link Response}
     * @throws Exception
     */
    Response findComment(int currIndex, int pageSize) throws Exception;

    Response findCommentByUserId(Integer userId, int currIndex, int pageSize) throws Exception;

    /**
     * 注销用户
     *
     * @param userId id
     * @return {@link Response}
     */
    Response deleteUser(int userId) throws Exception;

    /**
     * 解锁
     * @param id 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response unlock(Integer id) throws Exception;

    /**
     * 冻结
     * @param id  用户
     * @param day 天数
     * @return {@link Response}
     * @throws Exception
     */
    Response lock(Integer id, Integer day) throws Exception;



}
