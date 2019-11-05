package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.UserInfoRequest;
import com.jryyy.forum.services.UserInfoService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户信息控制层
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/{id}")
    public Response findOtherUserInformation(@PathVariable Integer id) throws Exception {
        return userInfoService.viewOtherPeopleSPersonalInformation(id);
    }

    @GetMapping("/condition")
    public Response queryUserListBasedOnCriteria(String value) throws Exception {
        return userInfoService.queryUserList(value);
    }

    @PutMapping("/img")
    @UserLoginToken
    public Response setBackgroundImage(MultipartFile file, HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userInfoService.modifyBackgroundImg(userId, file);
    }

    @PutMapping
    @UserLoginToken
    public Response addOrModifyUserInformation(@Valid @ModelAttribute UserInfoRequest request,
                                               HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        request.setUserId(userId);
        return userInfoService.changeUserPersonalBasicInformation(request);
    }

    @GetMapping
    @UserLoginToken
    public Response viewUserProfile(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userInfoService.viewUserPersonalInformation(userId);
    }

    @PostMapping("/check")
    @UserLoginToken
    public Response checkIn(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userInfoService.checkIn(userId);
    }

    @GetMapping("/check")
    @UserLoginToken
    public Response judgeWhetherToCheckIn(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userInfoService.whetherToCheckIn(userId);
    }
}
