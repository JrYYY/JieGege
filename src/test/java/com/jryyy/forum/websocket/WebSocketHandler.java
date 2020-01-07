package com.jryyy.forum.websocket;


import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import java.io.IOException;
import java.util.*;

/**
 * 未采用 的  webSocket 配置
 * @author OU
 */
@Slf4j
//@Component
//@ServerEndpoint(value = "/webSocket/{userId}",configurator = SpringConfigurator.class)
public class WebSocketHandler {

    private static  final Map<Integer, WebSocketHandler> USER_SOCKET = new HashMap<>();

    /**
     * 需要session来对用户发送数据
     */
    private Session session;

    /**
     * 用户标识key
     */
    private Integer userId;


    /**
     * @Title: onOpen
     * @Description: webSocket连接建立时的操作
     * @param  userId 用户id
     * @param  session websocket连接的session属性
     */
    @OnOpen
    public void onOpen(@PathVariable Integer userId,Session session) throws GlobalException, IOException {
        this.userId = userId;
        this.session = session;
        if(USER_SOCKET.containsKey(this.userId)){
            log.debug("当前用户id:{}已有其他终端登录",this.userId);
            throw new GlobalException(GlobalStatus.alreadyLoggedInElsewhere);
        }else {
            log.debug("当前用户id:{}终端登录",this.userId);
            USER_SOCKET.put(this.userId, this);
        }
    }

    /**
     * @Title: onClose
     * @Description: 连接关闭的操作
     */
    @OnClose
    public void  onClose(){
       USER_SOCKET.remove(this.userId);
       log.debug("用户{}退出",this.userId);
       log.debug("当前在线用户数为：{}",USER_SOCKET.size());
    }

    /**
     * @Title: onMessage
     * @Description: 收到消息后的操作
     * @param message 收到的消息
     * @param session 该连接的session属性
     */
    @OnMessage
    public void onMessage(String message,Session session){
        log.debug("收到来自用户id为：{}的消息：{}",this.userId,message);
        if(session == null){  log.debug("session null");}
    }

    /**
     * @Title: onError
     * @Description: 连接发生错误时候的操作
     * @param  session 该连接的session
     * @param  error 发生的错误
     */
    @OnError
    public void onError(Session session,Throwable error)throws Exception{
        log.debug("用户id为：{}的连接发送错误",this.userId);
        error.printStackTrace();
        throw new GlobalException(GlobalStatus.serverError);
    }
    

    /**
     * @Title: sendMessageToUser
     * @Description: 发送消息给用户下的所有终端
     * @param  userId 用户id
     * @param  message 发送的消息
     * @return 发送成功返回true，反则返回false
     */
    public Boolean sendMessageToUser(Message message) throws GlobalException {
        if (USER_SOCKET.containsKey(userId)) {
            log.debug(" 给用户id为：{}的所有终端发送消息：{}", userId, message);
            WebSocketHandler ws = USER_SOCKET.get(message.getTo());
            try {
                ws.session.getBasicRemote().sendObject(message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                log.debug(" 给用户id为：{}发送消息失败",userId);
               throw new GlobalException(GlobalStatus.serverError);
            } 
        }
        return false;
    }


}

