package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 绑定 服务
 */
public interface BindingService {
    /**
     * 查看绑定列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    Response queryAllAssociatedUsers(int userId) throws Exception;

    /**
     * 添加绑定
     *
     * @param userId
     * @param email
     * @return
     * @throws Exception
     */
    Response addAssociatedUsers(int userId, String email) throws Exception;

    /**
     * 删除绑定
     *
     * @param userId
     * @param id
     * @return
     * @throws Exception
     */
    Response deleteAssociatedUsers(int userId, int id) throws Exception;

    /**
     * 确认绑定
     *
     * @param boundId
     * @param id
     * @return
     * @throws Exception
     */
    Response confirmBinding(int boundId, int id) throws Exception;

    /**
     * 拒绝绑定
     *
     * @param boundId
     * @param id
     * @return
     * @throws Exception
     */
    Response refuseBind(int boundId, int id) throws Exception;
}
