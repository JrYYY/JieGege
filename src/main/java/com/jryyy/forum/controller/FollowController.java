package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.FollowService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 * @author JrYYY
 */
@RestController
@UserLoginToken
@RequestMapping("/user")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/following/{userId}")
    public Response following(@PathVariable Integer userId) throws Exception {
        return followService.viewWatchlist(userId);
    }


    @GetMapping("/followers/{userId}")
    public Response followers(@PathVariable Integer userId) throws Exception {
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
