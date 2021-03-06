package com.jryyy.forum.service;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;

import java.util.List;

/**
 *webSocket访问 （启用）
 * @author OU
 */
public interface WebsocketService {

    /**
     *  群聊
     * @param message {@link Message}
     * @return  {@link Response}
     * @throws Exception
     */
    Response groupChat(Message message)throws Exception;

    /**
     * 单聊
     * @param message   {@link Message}
     * @return {@link Response}
     * @throws Exception
     */
    Response singleChat(Message message)throws Exception;

    /**
     * 查询历史记录
     * @param userId 用户id
     * @param id 查询id
     * @return {@link List<Message>}
     * @throws Exception
     */
    Response queryHistory(Integer userId,Integer id)throws Exception;

    /**
     *
     * @param id 群组id
     * @return  {@link List<Message>}
     * @throws Exception
     */
    Response queryHistory(Integer id)throws Exception;

    /**
     * 查询用户离线记录
     * @param status    群聊或者单聊
     * @param userId    查询用户的id
     * @return {@link Response}
     * @throws Exception
     */
    Response userOfflineMessage(String status,Integer userId)throws Exception;

    /**
     * 在线人数
     * @return 人数
     * @throws Exception
     */
    Response onLine()throws Exception;

    /**
     * 判断该用户是否在线
     * @param userId    用户id
     * @return  true|false
     * @throws Exception
     */
    Response onLine(Integer userId)throws Exception;
}
