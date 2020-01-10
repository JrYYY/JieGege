package com.jryyy.forum.controller;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebSocketService;
import com.jryyy.forum.utils.FileUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;

/**
 * @author OU
 */
@Slf4j
@RestController
@MessageMapping("/send")
public class WebSocketController {

    private final FileUtils fileUtils;

    private final WebSocketService webSocketService;

    public WebSocketController(WebSocketService webSocketService, FileUtils fileUtils) {
        this.webSocketService = webSocketService;
        this.fileUtils = fileUtils;
    }

    @MessageMapping("/group")
    public Response groupChat(@RequestBody @Valid Message message) throws Exception {
        return webSocketService.groupChat(message);
    }

    @MessageMapping("/single")
    public Response singleChat(@RequestBody @Valid Message message) throws Exception {
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

    @UserLoginToken
    @PostMapping("/send/image/{userId}")
    public Response handleUploadImage(@PathVariable Integer userId, MultipartFile image)throws Exception{
        return null;
    }
}
