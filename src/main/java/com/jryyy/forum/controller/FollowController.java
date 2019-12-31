package com.jryyy.forum.controller;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.FollowService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 * @author JrYYY
 */
@RestController
@UserLoginToken
@RequestMapping("/user")
public class FollowController {

    @Autowired
    FollowService followService;

    @GetMapping("/following")
    public Response following(@RequestParam Integer userId) throws Exception {
        return followService.viewWatchlist(userId);
    }


    @GetMapping("/followers")
    public Response followers(@RequestParam Integer userId) throws Exception {
        return followService.viewFanList(userId);
    }

    @PostMapping("/follow/{id}")
    public Response addFollow(@PathVariable int id, @RequestParam Integer userId) throws Exception {
        return followService.attention(userId, id);
    }

    @DeleteMapping("/follow/{id}")
    public Response nuFollow(@PathVariable int id,@RequestParam Integer userId) throws Exception {
        return followService.takeOff(userId,id);
    }

    @GetMapping("/judgment")
    public Response judgment(@RequestParam String email,@RequestParam Integer userId) throws Exception {
        return followService.judgedHasBeenConcerned(userId,email);
    }
}
