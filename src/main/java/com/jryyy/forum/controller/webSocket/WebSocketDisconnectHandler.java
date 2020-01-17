package com.jryyy.forum.controller.webSocket;


import com.jryyy.forum.constant.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * websocket断开连接处理，监听SessionDisconnectEvent事件，当有人下线就通知其他用户
 * @author OU
 */
@Slf4j
@Component
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate messagingTemplate;

    private final RedisTemplate<String,Object> redisTemplate;

    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    public WebSocketDisconnectHandler(SimpMessagingTemplate messagingTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 当sessionDisconnectEvent发布时，此方法将被调用，从事件中的message取出websocket sessionAttributes
     * 从中取出离开的User，将在线用户map中删除该用户，通知其他用户
     * @param sessionDisconnectEvent {@link SessionDisconnectEvent}
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        log.info("logout:---------"+accessor.getSessionId());
        String userId =(String) redisTemplate.opsForHash().get(RedisKey.ONLINE_USER_LIST_KEY,accessor.getSessionId());
        redisTemplate.opsForHash().delete(RedisKey.ONLINE_USER_LIST_KEY,accessor.getSessionId());
        redisTemplate.opsForHash().delete(RedisKey.ONLINE_USER_LIST_KEY,RedisKey.userKey(userId));
    }
}
