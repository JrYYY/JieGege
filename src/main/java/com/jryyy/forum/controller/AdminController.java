package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.AdminService;
import com.jryyy.forum.utils.security.UserLoginToken;
import com.jryyy.forum.utils.security.UserRoleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制层
 * @author JrYYY
 */
@Slf4j
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
    @PutMapping("/unlock")
    public Response unlock(@RequestParam Integer id) throws Exception {
        adminService.unlock(id);
        return new Response<>(true);
    }

    /**
     * 锁用户
     *
     * @param id 用户id
     */
    @PutMapping("/lock")
    public Response lock(@RequestParam Integer id, @RequestParam Integer day) throws Exception {
        log.info(id + " " + day);
        adminService.lock(id, day);
        return new Response<>(true);
    }

    /**
     * 查询所有用户‘
     *
     * @return {@link com.jryyy.forum.model.response.AdminFindUserResponse}
     * @throws Exception {}
     */
    @GetMapping("/users")
    public Response findAllUsers() throws Exception {
        return adminService.findAllUsers();
    }

    @GetMapping("/comment")
    public Response findComment(@RequestParam int currIndex, @RequestParam int pageSize) throws Exception {
        return adminService.findComment(currIndex, pageSize);
    }

    @GetMapping("/comment/user")
    public Response findCommentByUserId(@RequestParam Integer id,
                                        @RequestParam int currIndex,
                                        @RequestParam int pageSize) throws Exception {
        return adminService.findCommentByUserId(id, currIndex, pageSize);
    }

}
