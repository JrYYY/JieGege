package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebSocketService;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;


import javax.validation.Valid;

/**
 * @author OU
 */
@Slf4j
@RestController
public class WebSocketController {

    private final WebSocketService webSocketService;

    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/send/group")
    public Response groupChat(@RequestBody @Valid Message message) throws Exception {
        return webSocketService.groupChat(message);
    }

    @MessageMapping("/send/single")
    public Response singleChat(@RequestBody @Valid Message message) throws Exception {
        //log.info(handler.toString());
        return webSocketService.singleChat(message);
    }

    @MessageMapping("/hello")
    @SendTo("/users/getResponse")
    public String say(@RequestBody Message message) throws Exception {
        log.info(message.toString()+"__________________________");
        return "Hello," + message.toString() + "!";
    }

    @UserLoginToken
    @GetMapping("/single/history/{userId}")
    public Response singleChatQueryHistory(@PathVariable Integer userId, @RequestParam Integer id)throws Exception{
        return webSocketService.queryHistory(userId,id);
    }

    @UserLoginToken
    @GetMapping("/group/history/{userId}")
    public Response groupChatQueryHistory(@PathVariable Integer userId, @RequestParam Integer id)throws Exception{
        return webSocketService.queryHistory(userId,id);
    }
}
