package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.Message;

import java.util.List;

/**
 *webSocket访问
 * @author OU
 */
public interface WebSocketService {

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
     * @param id
     * @return
     * @throws Exception
     */
    Response queryHistory(Integer id)throws Exception;
}
