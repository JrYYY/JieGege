package com.jryyy.forum.config;

import com.jryyy.forum.config.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Spring websocket配置类
 * @author OU
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 连接 webSocket订阅节点，
     */
    private static final String SUBSCRIPTION_NODE = "/websocket";

    /**
     * 定义接收/websocket时采用webSocket连接，添加HttpSessionHandshakeInterceptor是为了websocket握手前将httpSession中的属性
     * 添加到websocket session中，withSockJS添加对sockJS的支持
     *
     * @param stompEndpointRegistry 踩踏端点注册表
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(SUBSCRIPTION_NODE).
                addInterceptors(webSocketInterceptor()).
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

    @Bean
    public WebSocketInterceptor webSocketInterceptor(){
        return new WebSocketInterceptor();
    }
}
