package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebSocketService;
import com.jryyy.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


/**
 * @author JrYYY
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {

    /**
     * 订阅组聊天消息地址
     */
    private static final String SUBSCRIBE_GROUP_CHAT_MESSAGE_URI = "/group/message";

    /**
     * 订阅单个聊天消息地址
     */
    private static final String SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI = "/single/message";

    /**
     * Simp消息传递模板
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Redis模板
     */
    private final RedisTemplate<String,Object> redisTemplate;

    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Response groupChat(Message message) throws Exception {
        log.info(message.toString());
        messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                SUBSCRIBE_GROUP_CHAT_MESSAGE_URI, message);
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        redisUtils.rightPushHashList(RedisKey.GROUP_CHAT_MESSAGE,
                RedisKey.groupMessageKey(message.getTo()),message);
        return new Response();

    }

    @Override
    public Response singleChat(Message message) throws Exception {
        log.info(message.toString());
        messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI,message);
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        redisUtils.rightPushHashList(RedisKey.SINGLE_CHAT_MESSAGE,
               RedisKey.userMessageKey(message.getTo(),message.getFrom()),message);
        return new Response();
    }

    @Override
    public Response queryHistory(Integer userId, Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        return new Response<>(redisUtils.getHashList(RedisKey.SINGLE_CHAT_MESSAGE,
                RedisKey.userMessageKey(userId,id),Message.class));
    }

    @Override
    public Response queryHistory(Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        return new Response<>(redisUtils.getHashList(RedisKey.GROUP_CHAT_MESSAGE,
                RedisKey.groupMessageKey(id),Message.class));
    }
}
