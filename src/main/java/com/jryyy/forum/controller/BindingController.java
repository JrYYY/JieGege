package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.BindingService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 绑定用户控制层
 */
@RestController
@RequestMapping("/binding")
public class BindingController {

    @Autowired
    BindingService bindingService;

    /**
     * 添加好友
     *
     * @param email   绑定用户邮箱
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @UserLoginToken
    @PostMapping
    public Response addBinding(@RequestParam String email, HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return bindingService.addAssociatedUsers(userId, email);
    }

    /**
     * 删除绑定
     *
     * @param id      绑定id
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @UserLoginToken
    @DeleteMapping("/{id}")
    public Response deleteBinding(@PathVariable("id") int id, HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return bindingService.deleteAssociatedUsers(userId, id);
    }

    /**
     * 用户绑定列表
     *
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @UserLoginToken
    @GetMapping
    public Response checkTheBinding(HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return bindingService.queryAllAssociatedUsers(userId);
    }

    /**
     * 确认绑定
     *
     * @param id      绑定id
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/confirm/{id}")
    @UserLoginToken
    public Response confirmBinding(@PathVariable("id") int id, HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return bindingService.confirmBinding(userId, id);
    }

    /**
     * 拒绝绑定
     *
     * @param id      绑定id
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @DeleteMapping("/refuse/{id}")
    @UserLoginToken
    public Response refuseBind(@PathVariable("id") int id, HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return bindingService.refuseBind(userId, id);
    }


}
