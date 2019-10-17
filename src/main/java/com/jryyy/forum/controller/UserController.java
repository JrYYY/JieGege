package com.jryyy.forum.controller;

import com.jryyy.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 解锁用户
     *
     * @param id 用户id
     */
    @PutMapping("/{id}/unlock")
    public void unlock(@PathVariable("id") long id) {
        // userService.unlock(id);
    }

    /**
     * 锁用户
     *
     * @param id 用户id
     */
    @PutMapping("/{id}/lock")
    public void lock(@PathVariable("id") long id, int day) {
        // userService.lock(id);
    }


}
