package com.jryyy.forum.controller;

import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.UserService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    /**
     * 解锁用户
     *
     * @param id 用户id
     */
    @UserLoginToken(role = RoleCode.ADMIN)
    @PutMapping("/{id}/unlock")
    public Response unlock(@PathVariable("id") long id) {
        // userService.unlock(id);
        return new Response();
    }

    /**
     * 锁用户
     *
     * @param id 用户id
     */
    @UserLoginToken(role = RoleCode.ADMIN)
    @PutMapping("/{id}/lock/{day}")
    public Response lock(@PathVariable("id") long id, @PathVariable("day") int day) {
        // userService.lock(id);
        return new Response();
    }

    /**
     * 查询所有用户‘
     *
     * @return {@link com.jryyy.forum.models.response.AdminFindUserResponse}
     * @throws Exception {}
     */
    @UserLoginToken(role = RoleCode.ADMIN)
    @GetMapping("/users")
    public Response findAllUsers() throws Exception {
        return userService.findAllUsers();
    }

    @UserLoginToken(role = RoleCode.ADMIN)
    @GetMapping("/zones")
    public Response findAllZones() throws Exception {
        return new Response();
    }

}
