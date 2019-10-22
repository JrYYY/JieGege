package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.ZoneRequest;

public interface ZoneService {

    /**
     * 写信息
     *
     * @param request {@link ZoneRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response writeZone(ZoneRequest request) throws Exception;

    /**
     * 查找所有空间
     *
     * @param curPage  页数
     * @param pageSize 多少
     * @param mode     查询模式
     * @return {@link com.jryyy.forum.models.response.ZoneListResponse}
     * @throws Exception
     */
    Response findAllZone(int curPage, int pageSize, int mode) throws Exception;

    /**
     * 查询用户空间
     *
     * @param curPage  页数
     * @param pageSize 多少
     * @param userId   用户id
     * @param mode     查询模式
     * @return {@link com.jryyy.forum.models.response.ZoneListResponse}
     * @throws Exception
     */
    Response findUserZone(int curPage, int pageSize, int userId, int mode) throws Exception;

    /**
     * 查询空间详情
     *
     * @param id 空间id
     * @return 详情
     * @throws Exception
     */
    Response findZoneById(int id) throws Exception;

    /**
     * 删除上传空间
     *
     * @param userId 用户id
     * @param id     id
     * @return {@link Response}
     * @throws Exception
     */
    Response deleteZoneById(int userId, int id) throws Exception;


    /**
     * 点赞或取消
     *
     * @param userId 用户id
     * @param zoneId 空间id
     * @return true/false
     * @throws Exception
     */
    Response likeOrCancel(int userId, int zoneId) throws Exception;

    /**
     * 判断是否已经点赞
     *
     * @param userId 用户id
     * @param zoneId 空间id
     * @return true/false
     * @throws Exception
     */
    Response liked(int userId, int zoneId) throws Exception;

}
