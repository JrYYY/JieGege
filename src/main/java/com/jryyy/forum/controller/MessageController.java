package com.jryyy.forum.controller;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.response.MessageResponse;
import com.jryyy.forum.service.MessageService;
import com.jryyy.forum.utils.FileUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author OU
 */
@RestController
@UserLoginToken
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    private final FileUtils fileUtils;

    @Value("${file.url}")
    private String fileUrl;

    private static final  String MESSAGE_IMAGE_URL = "/message/";

    public MessageController(MessageService messageService,FileUtils fileUtils) {
        this.messageService = messageService;
        this.fileUtils = fileUtils;
    }

    /**
     * 发送消息
     * @param userId 用户id
     * @param message   消息
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/send/{userId}")
    public Response sendMessage(@PathVariable Integer userId, @ModelAttribute Message message)throws Exception{
        message.setFrom(userId);
        return messageService.sendMessage(message);
    }

    /**
     * 读取未读消息
     * @param userId   用户id
     * @param from  发
     * @return  {@link Message}
     * @throws Exception
     */
    @GetMapping("/read/{userId}")
    public Response readMessage(@PathVariable Integer userId,@RequestParam Integer from)throws Exception{
        return messageService.readMessage(userId,from);
    }

    /**
     * 未读信息列表
     * @param userId 用户id
     * @return {@link MessageResponse}
     * @throws Exception
     */
    @GetMapping("/unchecked/{userId}")
    public Response uncheckedMessages(@PathVariable Integer userId)throws Exception{
        return messageService.uncheckedMessages(userId);
    }

    @PostMapping("/image/{userId}")
    public String saveFile(@PathVariable Integer userId,MultipartFile file)throws Exception{
        return fileUrl + fileUtils.saveTalkImg(MESSAGE_IMAGE_URL+userId+"/",file);
    }


}
