package com.jryyy.forum.config;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import com.jryyy.forum.utils.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;

/**
 * Spring websocket配置类
 * @author OU
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 连接 webSocket订阅节点，
     */
    private static final String SUBSCRIPTION_NODE = "/websocket";

    private final TokenUtils tokenUtils;

    private final RedisTemplate<String, Object> redisTemplate;

    public WebSocketConfiguration(TokenUtils tokenUtils, RedisTemplate<String, Object> redisTemplate) {
        this.tokenUtils = tokenUtils;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 定义接收/websocket时采用webSocket连接，添加HttpSessionHandshakeInterceptor是为了websocket握手前将httpSession中的属性
     * 添加到websocket session中，withSockJS添加对sockJS的支持
     *
     * @param stompEndpointRegistry 踩踏端点注册表
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(SUBSCRIPTION_NODE).
                addInterceptors(new HttpSessionHandshakeInterceptor()).
                setAllowedOrigins("*");
    }

    /**
     * 配置消息代理，以/app为头的url将会先经过MessageMapping
     * /topic直接进入消息代理
     *
     * @param registry 邮件代理注册中心
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * spring 内置broker对象
         * 1. 配置代理域，可以配置多个，此段代码配置代理目的地的前缀为 /users 或者 /user
         */
        registry.enableSimpleBroker("/users","/user");
        registry.setUserDestinationPrefix("/user/");

    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if(accessor != null ) {
                    StompCommand messageType = accessor.getCommand();
                    if (messageType != null && messageType.equals(StompCommand.CONNECT)) {
                       List<String> header = accessor.getNativeHeader(Constants.USER_TOKEN_STRING);
                        String token = header.get(0);
                        try {
                            User user = tokenUtils.decodeJwtToken(token);
                            log.info("login:"+user.getId());
                            redisTemplate.opsForHash().put(RedisKey.ONLINE_USER_LIST_KEY,accessor.getSessionId(),user.getId());
                            redisTemplate.opsForHash().put(RedisKey.ONLINE_USER_LIST_KEY,RedisKey.userKey(user.getId()),accessor.getSessionId());
                        } catch (GlobalException e) {
                            return message;
                        }
                    }
                }
                return message;
            }
        });
    }

}
