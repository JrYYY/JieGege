package com.jryyy.forum.service.imp;

import com.alibaba.fastjson.TypeReference;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayAndUrl;
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
import java.util.*;


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
        if (!inquire(KayAndUrl.ONLINE_USER_LIST_KEY, message.getFrom())){
            throw new GlobalException(GlobalStatus.userNotLogin);
        }
        if(userMapper.findUserById(message.getTo()) == null){
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        if (inquire(KayAndUrl.ONLINE_USER_LIST_KEY, message.getTo())) {
            log.info(message.toString());
            List<GroupMember> groupMembers = groupMapper.findMemberByGroupId(message.getTo());
            groupMembers.forEach(member -> messagingTemplate.convertAndSendToUser(
                    member.getUserId().toString(),
                    SUBSCRIBE_GROUP_CHAT_MESSAGE_URI, message));
            redisUtils.rightPushHashList(KayAndUrl.GROUP_CHAT_MESSAGE_KEY,
                    KayAndUrl.groupMessageKey(message.getTo()), message);
        } else {
            redisUtils.rightPushHashList(KayAndUrl.GROUP_CHAT_MESSAGE_KEY,
                    KayAndUrl.userMessageKey(message.getFrom(), message.getTo()), message);
            Map<Integer, Message> messageMap = redisUtils.getHashMap(
                    KayAndUrl.USER_GROUP_CHAT_OFFLINE_RECORD_KEY,
                    KayAndUrl.userKey(message.getTo()),
                    new TypeReference<Map<Integer, Message>>(){});
            messageMap = messageMap == null ? new HashMap<>() : messageMap;
            messageMap.put(message.getFrom(), message);
            redisUtils.setHashMap(KayAndUrl.USER_GROUP_CHAT_OFFLINE_RECORD_KEY,
                    KayAndUrl.userKey(message.getTo()), messageMap);
        }

        return new Response();

    }

    @Override
    public Response singleChat(Message message) throws Exception {
        message.setDate(LocalDateTime.now());
        log.info(message.toString());
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        if (!inquire(KayAndUrl.ONLINE_USER_LIST_KEY, message.getFrom())){
            throw new GlobalException(GlobalStatus.userNotLogin);
        }
        if(userMapper.findUserById(message.getTo()) == null){
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        if (inquire(KayAndUrl.ONLINE_USER_LIST_KEY, message.getTo())) {
            if (userMapper.findUserById(message.getTo()) == null) {
                throw new GlobalException(GlobalStatus.userDoesNotExist);
            }
            log.info(message.toString());
            messagingTemplate.convertAndSendToUser(message.getTo().toString(),
                    SUBSCRIBE_SINGLE_CHAT_MESSAGE_URI, message);
            redisUtils.rightPushHashList(KayAndUrl.SINGLE_CHAT_MESSAGE_KEY,
                    KayAndUrl.userMessageKey(message.getFrom(), message.getTo()), message);
        } else {
            redisUtils.rightPushHashList(KayAndUrl.SINGLE_CHAT_MESSAGE_KEY,
                    KayAndUrl.userMessageKey(message.getFrom(), message.getTo()), message);
            Map<Integer, Message> messageMap = redisUtils.getHashMap(
                    KayAndUrl.USER_SINGLE_CHAT_OFFLINE_RECORD_KEY,
                    KayAndUrl.userKey(message.getTo()),
                    new TypeReference<Map<Integer, Message>>() {});
            messageMap = messageMap == null ? new HashMap<>() : messageMap;
            messageMap.put(message.getFrom(), message);
            redisUtils.setHashMap(KayAndUrl.USER_SINGLE_CHAT_OFFLINE_RECORD_KEY,
                    KayAndUrl.userKey(message.getTo()),messageMap);
        }
        return new Response();
    }

    @Override
    public Response queryHistory(Integer userId, Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        List<Message> messages = redisUtils.getHashList(KayAndUrl.SINGLE_CHAT_MESSAGE_KEY,
                KayAndUrl.userMessageKey(userId,id),Message.class);
        if(userId.equals(id)){
            messages.sort(Comparator.comparing(Message::getDate));
            return new Response<>(messages);
        }
        messages.addAll(redisUtils.getHashList(KayAndUrl.SINGLE_CHAT_MESSAGE_KEY,
                KayAndUrl.userMessageKey(id,userId),Message.class));
        messages.sort(Comparator.comparing(Message::getDate));
        return new Response<>(messages);
    }

    @Override
    public Response queryHistory(Integer id) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        return new Response<>(redisUtils.getHashList(KayAndUrl.GROUP_CHAT_MESSAGE_KEY,
                KayAndUrl.groupMessageKey(id),Message.class));
    }

    @Override
    public Response userOfflineMessage(String status, Integer userId) throws Exception {
        RedisUtils redisUtils = new RedisUtils(redisTemplate);
        Map<Integer, Message> messageMap = redisUtils.getHashMap(
                status, KayAndUrl.userKey(userId),
                new TypeReference<Map<Integer, Message>>() {});
        messageMap = messageMap == null ? new HashMap<>() : messageMap;
        return new Response<>(messageMap);
    }

    @Override
    public Response onLine() throws Exception {
        Long size = redisTemplate.opsForList().size(KayAndUrl.ONLINE_USER_LIST_KEY);
        return new Response<>(size == null ? 0 : size / 2);
    }



    @Override
    public Response onLine(Integer userId) throws Exception {
        Object id = redisTemplate.opsForHash().get(KayAndUrl.ONLINE_USER_LIST_KEY, KayAndUrl.userKey(userId));
        return new Response<>(id != null);
    }

    private Boolean inquire(String key,Integer userId)  {
        Object sessionId = redisTemplate.opsForHash().get(key, KayAndUrl.userKey(userId));
        return sessionId != null;
    }

    private void userStatus(Integer userId,Integer groupId)throws Exception{

    }



}
