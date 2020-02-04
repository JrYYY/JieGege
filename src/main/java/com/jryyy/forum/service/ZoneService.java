package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.GetZoneRequest;
import com.jryyy.forum.model.request.ZoneRequest;

/**
 * @author OU
 */
public interface ZoneService {

    /**
     * 写入空间
     * @param request   {@link ZoneRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response insertZone(ZoneRequest request)throws Exception;

    /**
     * 读取空间
     * @param userId 用户id
     * @param request   {@link GetZoneRequest}
     * @return  {@link com.jryyy.forum.model.response.ZoneResponse}
     * @throws Exception
     */
    Response getZone(Integer userId,GetZoneRequest request)throws Exception;

    /**
     * 删除空间
     * @param userId    用户id
     * @param zoneId    空间id
     * @return  {@link Response}
     * @throws Exception
     */
    Response deleteZone(Integer userId,Integer zoneId)throws Exception;

    /**
     * 空间评论
     * @param zoneId 空间id
     * @param currIndex 当前页
     * @param pageSize  总页数
     * @return  {@link com.jryyy.forum.model.response.ZoneCommentResponse }
     * @throws Exception
     */
    Response findZoneComment(Integer zoneId,Integer currIndex,Integer pageSize)throws Exception;

    /**
     *查看回复
     * @param id    评论id
     * @return {@link com.jryyy.forum.model.response.ZoneCommentResponse }
     * @throws Exception
     */
    Response findReply(Integer id)throws Exception;



    /**
     *评论
     * @param userId    用户id
     * @param id        空间id
     * @param comment   评论
     * @param pId 关联id
     * @return {@link Response}
     * @throws Exception
     */
    Response comment(Integer userId, Integer id,String comment,Integer pId)throws Exception;

    /**
     * 点赞
     * @param userId    用户id
     * @param zoneId    空间id
     * @return  {@link Response}
     * @throws Exception
     */
    Response like(Integer userId,Integer zoneId)throws Exception;

}
