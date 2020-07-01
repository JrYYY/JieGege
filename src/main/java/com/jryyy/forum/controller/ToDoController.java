package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.ToDoService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author OU
 */
@UserLoginToken
@RestController
@RequestMapping("/toDo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }


    /**
     * 添加目标
     *
     * @param userId 用户id
     * @param target 目标
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    public Response addTarget(@PathVariable Integer userId,
                              @RequestParam String target) throws Exception {
        return toDoService.addTarget(userId, target);
    }

    /**
     * 修改目标
     *
     * @param userId 用户id
     * @param id     主键
     * @param target 目标
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/{userId}")
    public Response editTarget(@PathVariable Integer userId,
                               @RequestParam Integer id,
                               @RequestParam String target) throws Exception {
        return toDoService.editTarget(userId, id, target);
    }

    /**
     * 删除目标
     *
     * @param userId 用户id
     * @param id     主键
     * @return {@link Response}
     * @throws Exception
     */
    @DeleteMapping("/{userId}")
    public Response deleteTarget(@PathVariable Integer userId,
                                 @RequestParam Integer id) throws Exception {
        return toDoService.deleteTarget(userId, id);
    }

    /**
     * 查询用户目标
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public Response findTargetByUserId(@PathVariable Integer userId) throws Exception {
        return toDoService.findTargetByUserId(userId);
    }
}
