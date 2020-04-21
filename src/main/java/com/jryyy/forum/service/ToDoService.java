package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 目标
 *
 * @author OU
 */
public interface ToDoService {
    /**
     * 添加目标
     *
     * @param userId 用户id
     * @param target 目标
     * @return {@link Response}
     * @throws Exception
     */
    Response addTarget(Integer userId, String target) throws Exception;

    /**
     * 修改目标
     *
     * @param userId 用户id
     * @param id     主键
     * @param target 目标
     * @return {@link Response}
     * @throws Exception
     */
    Response editTarget(Integer userId, Integer id, String target) throws Exception;

    /**
     * 删除目标
     *
     * @param userId 用户id
     * @param id     主键
     * @return {@link Response}
     * @throws Exception
     */
    Response deleteTarget(Integer userId, Integer id) throws Exception;

    /**
     * 查询用户目标
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response findTargetByUserId(Integer userId) throws Exception;
}
