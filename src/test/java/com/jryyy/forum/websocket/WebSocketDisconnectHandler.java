package com.jryyy.forum.websocket;


import com.jryyy.forum.model.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * websocket断开连接处理，监听SessionDisconnectEvent事件
 * @author OU
 */
@Component
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate messagingTemplate;


    private final ParticipantRepository participantRepository;


    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    public WebSocketDisconnectHandler(SimpMessagingTemplate messagingTemplate ,ParticipantRepository participantRepository) {
        this.messagingTemplate = messagingTemplate;
        this.participantRepository = participantRepository;
    }

    /**
     * 当sessionDisconnectEvent发布时，此方法将被调用，从事件中的message取出websocket sessionAttributes
     * 从中取出离开的User，将在线用户map中删除该用户，通知其他用户
     * @param sessionDisconnectEvent 会话断开事件
     */
    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
//        Map<Integer, User> activeSessions = participantRepository.getActiveSessions();
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
//        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
//        User disconnectSession = (User) sessionAttributes.get("user");
//        Integer disconnectUserId = disconnectSession.getId();
//        if (participantRepository.containsUserName(disconnectUserId)){
//            User removeUser = participantRepository.remove(disconnectUserId);
//            messagingTemplate.convertAndSend(SUBSCRIBE_LOGOUT_URI, removeUser);
//        }
    }
}
