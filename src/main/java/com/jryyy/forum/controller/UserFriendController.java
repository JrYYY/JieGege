package com.jryyy.forum.controller;

import com.jryyy.forum.annotation.UserLoginToken;
import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/users")
public class UserFriendController {

    @Autowired
    UserFriendService userFriendService;


    @UserLoginToken
    @GetMapping("/following")
    public Response following(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userFriendService.viewWatchlist(userId);
    }


    @UserLoginToken
    @GetMapping("/followers")
    public Response followers(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return userFriendService.viewFanList(userId);
    }


    @UserLoginToken
    @PostMapping("/follow")
    public Response addFollow(@RequestParam String email, HttpSession session) throws Exception {
        int userId = (Integer) session.getAttribute(Constants.USER_ID_STRING);
        return userFriendService.attention(userId, email);
    }

    @UserLoginToken
    @DeleteMapping("/follow/{id}")
    public Response nuFollow(@PathVariable("id") int id, HttpSession session) throws Exception {
        int userId = (Integer) session.getAttribute(Constants.USER_ID_STRING);
        return userFriendService.takeOff(userId, id);
    }


}
