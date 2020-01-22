package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.service.UserInfoService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


/**
 * @author OU
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }

    @GetMapping("/search")
    public Response searchUser(@RequestParam String info) throws Exception {
        return userInfoService.searchUser(info);
    }

    @GetMapping("/info/{userId}")
    public Response viewUserInfo(@PathVariable Integer userId) throws Exception {
        return userInfoService.viewUserInfo(userId);
    }

    @UserLoginToken
    @PutMapping("/info/{userId}")
    public Response updateUserInfo(@PathVariable Integer userId, @Valid @ModelAttribute UserInfoRequest userInfoRequest) throws Exception{
        userInfoRequest.setUserId(userId);
        return userInfoService.changeUserInfo(userInfoRequest);
    }

    @UserLoginToken
    @PutMapping("/avatar/{userId}")
    public Response updateUserAvatar(@PathVariable Integer userId,@ModelAttribute @RequestParam MultipartFile image)throws Exception{
        return userInfoService.updateAvatar(userId,image);
    }

    @UserLoginToken
    @PutMapping("/background/{userId}")
    public Response updateUserBgImg(@PathVariable Integer userId,@ModelAttribute @RequestParam MultipartFile image)throws Exception{
        return userInfoService.updateBgImg(userId,image);
    }

    @UserLoginToken
    @PutMapping("/checkIn/{userId}")
    public Response checkIn(@PathVariable Integer userId)throws Exception{
        return userInfoService.checkIn(userId);
    }
}
