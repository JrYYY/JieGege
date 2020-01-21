package com.jryyy.forum.controller;

import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.WebsocketService;
import com.jryyy.forum.utils.FileUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;

/**
 * @author OU
 */
@Slf4j
@RestController
@MessageMapping("/send")
public class WebsocketController {

    private final FileUtils fileUtils;

    private final WebsocketService websocketService;

    public WebsocketController(WebsocketService websocketService, FileUtils fileUtils) {
        this.websocketService = websocketService;
        this.fileUtils = fileUtils;
    }

    @MessageMapping("/group")
    public Response groupChat(@RequestBody @Valid Message message) throws Exception {
        return websocketService.groupChat(message);
    }

    @MessageMapping("/single")
    public Response singleChat(@RequestBody @Valid Message message) throws Exception {
        return websocketService.singleChat(message);
    }

    @MessageMapping("/hello")
    public String say(@RequestBody Message message) throws Exception {
        log.info(message.toString()+"__________________________");
        return "Hello," + message.toString() + "!";
    }

    @UserLoginToken
    @GetMapping("/single/history/{userId}")
    public Response singleChatQueryHistory(@PathVariable Integer userId, @RequestParam Integer id)throws Exception{
        return websocketService.queryHistory(userId,id);
    }

    @UserLoginToken
    @GetMapping("/group/history/{userId}")
    public Response groupChatQueryHistory(@PathVariable Integer userId, @RequestParam Integer id)throws Exception{
        return websocketService.queryHistory(userId,id);
    }

    @UserLoginToken
    @GetMapping("/group/offlineMessage/{userId}")
    public Response groupChatOfflineHistory(@PathVariable Integer userId)throws Exception{
        return websocketService.userOfflineMessage
                (RedisKey.USER_GROUP_CHAT_OFFLINE_RECORD_KEY,userId);
    }

    @UserLoginToken
    @GetMapping("/single/offlineMessage/{userId}")
    public Response singleChatOfflineHistory(@PathVariable Integer userId)throws Exception{
        return websocketService.userOfflineMessage(
                RedisKey.USER_SINGLE_CHAT_OFFLINE_RECORD_KEY,userId);
    }

    @UserLoginToken
    @PostMapping("/send/image/{userId}")
    public Response handleUploadImage(@PathVariable Integer userId, MultipartFile image)throws Exception{
        return null;
    }
    
}
