package com.jryyy.forum.service.imp;

import com.alibaba.fastjson.TypeReference;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.dao.GroupMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.GroupMember;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebsocketService;
import com.jryyy.forum.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author JrYYY
 */
@Slf4j
@Service
public class WebsocketServiceImpl implements WebsocketService {

    /**
     * 订阅组聊天消息地址
     */
    private static final String SUBSCRIBE_GROUP_CHAT_MESSAGE_URI = "/group/message";

    /**
     * 订阅单个聊天消息地址
     */
    private static final String SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI = "/single/message";

    /**
     * 用户数据库处理
     */
    private final UserMapper userMapper;

    /**
     * 群组数据库处理成
     */
    private final GroupMapper groupMapper;

    /**
     * Simp消息传递模板
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Redis模板
     */
    private final RedisTemplate<String,Object> redisTemplate;

    public WebsocketServiceImpl(UserMapper userMapper, GroupMapper groupMapper, SimpMessagingTemplate messagingTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.groupMapper = groupMapper;
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Response groupChat(Message message) throws Exception {
        message.setDate(LocalDateTime.now());
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        if (!inquire(KayOrUrl.ONLINE_USER_LIST_KEY, message.getFrom())){
            throw new GlobalException(GlobalStatus.userNotLogin);
        }
        if(userMapper.findUserById(message.getTo()) == null){
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        if (inquire(KayOrUrl.ONLINE_USER_LIST_KEY, message.getTo())) {
            log.info(message.toString());
            List<GroupMember> groupMembers = groupMapper.findMemberByGroupId(message.getTo());
            groupMembers.forEach(member -> messagingTemplate.convertAndSendToUser(
                    member.getUserId().toString(),
                    SUBSCRIBE_GROUP_CHAT_MESSAGE_URI, message));
            redisUtils.rightPushHashList(KayOrUrl.GROUP_CHAT_MESSAGE_KEY,
                    KayOrUrl.groupMessageKey(message.getTo()), message);
        } else {
            redisUtils.rightPushHashList(KayOrUrl.GROUP_CHAT_MESSAGE_KEY,
                    KayOrUrl.userMessageKey(message.getFrom(), message.getTo()), message);
            Map<Integer, Message> messageMap = redisUtils.getHashMap(
                    KayOrUrl.USER_GROUP_CHAT_OFFLINE_RECORD_KEY,
                    KayOrUrl.userKey(message.getTo()),
                    new TypeReference<Map<Integer, Message>>(){});
            messageMap = messageMap == null ? new HashMap<>() : messageMap;
            messageMap.put(message.getFrom(), message);
            redisUtils.setHashMap(KayOrUrl.USER_GROUP_CHAT_OFFLINE_RECORD_KEY,
                    KayOrUrl.userKey(message.getTo()), messageMap);
        }
        return new Response();
    }

    @Override
    public Response singleChat(Message message) throws Exception {
        message.setDate(LocalDateTime.now());
        log.info(message.toString());
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        if (!inquire(KayOrUrl.ONLINE_USER_LIST_KEY, message.getFrom())){
            throw new GlobalException(GlobalStatus.userNotLogin);
        }
        if(userMapper.findUserById(message.getTo()) == null){
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        if (inquire(KayOrUrl.ONLINE_USER_LIST_KEY, message.getTo())) {
            if (userMapper.findUserById(message.getTo()) == null) {
                throw new GlobalException(GlobalStatus.userDoesNotExist);
            }
            messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                    SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI, message);
            redisUtils.rightPushHashList(KayOrUrl.SINGLE_CHAT_MESSAGE_KEY,
                    KayOrUrl.userMessageKey(message.getFrom(), message.getTo()), message);
        } else {
            redisUtils.rightPushHashList(KayOrUrl.SINGLE_CHAT_MESSAGE_KEY,
                    KayOrUrl.userMessageKey(message.getFrom(), message.getTo()), message);
            Map<Integer, Message> messageMap = redisUtils.getHashMap(
                    KayOrUrl.USER_SINGLE_CHAT_OFFLINE_RECORD_KEY,
                    KayOrUrl.userKey(message.getTo()),
                    new TypeReference<Map<Integer, Message>>() {});
            messageMap = messageMap == null ? new HashMap<>() : messageMap;
            messageMap.put(message.getFrom(), message);
            redisUtils.setHashMap(KayOrUrl.USER_SINGLE_CHAT_OFFLINE_RECORD_KEY,
                    KayOrUrl.userKey(message.getTo()),messageMap);
        }
        return new Response();
    }

    @Override
    public Response queryHistory(Integer userId, Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        List<Message> messages = redisUtils.getHashList(KayOrUrl.SINGLE_CHAT_MESSAGE_KEY,
                KayOrUrl.userMessageKey(userId,id),Message.class);
        if(userId.equals(id)){
            messages.sort(Comparator.comparing(Message::getDate));
            return new Response<>(messages);
        }
        messages.addAll(redisUtils.getHashList(KayOrUrl.SINGLE_CHAT_MESSAGE_KEY,
                KayOrUrl.userMessageKey(id,userId),Message.class));
        messages.sort(Comparator.comparing(Message::getDate));
        return new Response<>(messages);
    }

    @Override
    public Response queryHistory(Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        return new Response<>(redisUtils.getHashList(KayOrUrl.GROUP_CHAT_MESSAGE_KEY,
                KayOrUrl.groupMessageKey(id),Message.class));
    }

    @Override
    public Response userOfflineMessage(String status, Integer userId) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        Map<Integer, Message> messageMap = redisUtils.getHashMap(
                status, KayOrUrl.userKey(userId),
                new TypeReference<Map<Integer, Message>>() {});
        messageMap = messageMap == null ? new HashMap<>() : messageMap;
        return new Response<>(messageMap);
    }

    @Override
    public Response onLine() throws Exception {
        Long size = redisTemplate.opsForList().size(KayOrUrl.ONLINE_USER_LIST_KEY);
        return new Response<>(size == null ? 0 : size / 2);
    }

    @Override
    public Response onLine(Integer userId) throws Exception {
        Object id = redisTemplate.opsForHash().get(KayOrUrl.ONLINE_USER_LIST_KEY, KayOrUrl.userKey(userId));
        return new Response<>(id != null);
    }

    private Boolean inquire(String key,Integer userId)  {
        Object sessionId = redisTemplate.opsForHash().get(key, KayOrUrl.userKey(userId));
        return sessionId != null;
    }

    private void userStatus(Integer userId,Integer groupId)throws Exception{

    }



}
