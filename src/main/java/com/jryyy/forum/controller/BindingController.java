package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.BindingService;
import com.jryyy.forum.utils.security.UserLoginToken;
import com.jryyy.forum.utils.security.UserRoleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 绑定用户
 *
 * @author OU
 */
@Slf4j
@RestController
@RequestMapping("/binding")
public class BindingController {


    private final BindingService bindingService;

    public BindingController(BindingService bindingService) {
        this.bindingService = bindingService;
    }

    /**
     * 查看绑定列表
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */

    @GetMapping("/{userId}")
    @UserLoginToken(role = UserRoleCode.PARENT)
    public Response queryAllAssociatedUsers(@PathVariable Integer userId) throws Exception {
        return bindingService.queryAllAssociatedUsers(userId);
    }

    /**
     * 添加绑定
     *
     * @param userId 用户id
     * @param email  邮箱
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    @UserLoginToken(role = UserRoleCode.PARENT)
    public Response addAssociatedUsers(@PathVariable Integer userId, @RequestParam String email) throws Exception {
        return bindingService.addAssociatedUsers(userId, email);
    }

    /**
     * 删除绑定
     *
     * @param userId 用户id
     * @param id     绑定id
     * @return {@link Response}
     * @throws Exception
     */
    @DeleteMapping("/{userId}")
    @UserLoginToken(role = UserRoleCode.PARENT)
    public Response deleteAssociatedUsers(@PathVariable Integer userId,
                                          @RequestParam Integer id) throws Exception {
        return bindingService.deleteAssociatedUsers(userId, id);
    }

    /**
     * 确认绑定
     *
     * @param boundId 绑定id
     * @param userId  用户id
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/{userId}")
    @UserLoginToken(role = UserRoleCode.CHILD)
    public Response confirm(@RequestParam int boundId, @PathVariable int userId,
                            @RequestParam boolean confirm) throws Exception {
        return confirm ? bindingService.confirmBinding(boundId, userId) :
                bindingService.refuseBind(boundId, userId);
    }


}
