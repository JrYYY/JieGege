package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.AdminService;
import com.jryyy.forum.utils.security.UserLoginToken;
import com.jryyy.forum.utils.security.UserRoleCode;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制层
 * @author JrYYY
 */

@RestController
@RequestMapping("/admin")
@UserLoginToken(role = UserRoleCode.ADMIN)
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 解锁用户
     * @param id 用户id
     */
    @PutMapping("/{id}/unlock")
    public Response unlock(@PathVariable("id") long id) {
        return new Response();
    }

    /**
     * 锁用户
     *
     * @param id 用户id
     */
    @PutMapping("/{id}/lock/{day}")
    public Response lock(@PathVariable("id") long id, @PathVariable("day") int day) {
        // userService.lock(id);
        return new Response();
    }

    /**
     * 查询所有用户‘
     *
     * @return {@link com.jryyy.forum.model.response.AdminFindUserResponse}
     * @throws Exception {}
     */
    @UserLoginToken(role = UserRoleCode.ADMIN)
    @GetMapping("/users")
    public Response findAllUsers() throws Exception {
        return adminService.findAllUsers();
    }

}
