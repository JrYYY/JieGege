package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebSocketService;
import com.jryyy.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


/**
 * @author JrYYY
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService
{

    /**
     * 订阅组聊天消息地址
     */
    private static final String SUBSCRIBE_GROUP_CHAT_MESSAGE_URI = "/group/message";

    /**
     * 订阅单个聊天消息地址
     */
    private static final String SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI = "/single/message";

    /**
     * {@link UserMapper}
     */
    private final UserMapper userMapper;

    /**
     * Simp消息传递模板
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Redis模板
     */
    private final RedisTemplate<String,Object> redisTemplate;

    public WebSocketServiceImpl(UserMapper userMapper, SimpMessagingTemplate messagingTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Response groupChat(Message message) throws Exception {
        log.info(message.toString());
        messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                SUBSCRIBE_GROUP_CHAT_MESSAGE_URI, message);
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        redisUtils.rightPushHashList(RedisKey.GROUP_CHAT_MESSAGE_KEY,
                RedisKey.groupMessageKey(message.getTo()),message);
        return new Response();

    }

    @Override
    public Response singleChat(Message message) throws Exception {
        log.info(message.toString());
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        if( inquire(RedisKey.ONLINE_USER_LIST_KEY,message.getTo())) {
            messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                    SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI, message);
            redisUtils.rightPushHashList(RedisKey.SINGLE_CHAT_MESSAGE_KEY,
                    RedisKey.userMessageKey(message.getFrom(), message.getTo()), message);
        }
        return new Response();
    }

    @Override
    public Response queryHistory(Integer userId, Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        List<Message> messages = redisUtils.getHashList(RedisKey.SINGLE_CHAT_MESSAGE_KEY,
                RedisKey.userMessageKey(userId,id),Message.class);
        if(userId.equals(id)){
            messages.sort(Comparator.comparing(Message::getDate));
            return new Response<>(messages);
        }
        messages.addAll(redisUtils.getHashList(RedisKey.SINGLE_CHAT_MESSAGE_KEY,
                RedisKey.userMessageKey(id,userId),Message.class));
        messages.sort(Comparator.comparing(Message::getDate));
        return new Response<>(messages);
    }

    @Override
    public Response queryHistory(Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        return new Response<>(redisUtils.getHashList(RedisKey.GROUP_CHAT_MESSAGE_KEY,
                RedisKey.groupMessageKey(id),Message.class));
    }

    @Override
    public Response onLine() throws Exception {
//        redisTemplate.opsForHash().s
        return new Response();
    }

    @Override
    public Response onLine(Integer userId) throws Exception {
        Object id = redisTemplate.opsForHash().get(RedisKey.ONLINE_USER_LIST_KEY,RedisKey.userKey(userId));
        return new Response<>(id != null);
    }

    private Boolean inquire(String key,Integer userId)  {
        Object id = redisTemplate.opsForHash().get(key,RedisKey.userKey(userId));
        return id != null;
    }

}
