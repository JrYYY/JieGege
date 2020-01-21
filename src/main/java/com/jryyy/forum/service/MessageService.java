package com.jryyy.forum.service;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;

/**
 *  消息服务
 * @author OU
 */
public interface MessageService {

    /**
     * 发送消息
     * @param message   消息
     * @return {@link Response}
     * @throws Exception
     */
    Response sendMessage(Message message)throws Exception;

    /**
     * 读取未读消息
     * @param to    到
     * @param from  发
     * @return  {@link Message}
     * @throws Exception
     */
    Response readMessage(Integer to,Integer from)throws Exception;

    /**
     * 未读信息列表
     * @param userId 用户id
     * @return {@link com.jryyy.forum.model.response.MessageResponse}
     * @throws Exception
     */
    Response uncheckedMessages(Integer userId)throws Exception;


}
