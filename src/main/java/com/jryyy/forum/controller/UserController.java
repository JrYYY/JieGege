package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.UserInfo;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.service.UserInfoService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author OU
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService){
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

    @PutMapping("/info/{userId}")
    @UserLoginToken
    public Response updateUserInfo(@PathVariable Integer userId, @Valid @ModelAttribute UserInfoRequest userInfoRequest) throws Exception{
        userInfoRequest.setUserId(userId);
        return userInfoService.changeUserInfo(userInfoRequest);
    }
}
