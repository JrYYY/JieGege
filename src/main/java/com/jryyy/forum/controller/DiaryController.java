package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.DiaryService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author OU
 */
@UserLoginToken
@RestController
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    /**
     * 添加目标
     *
     * @param userId  用户id
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    public Response addDiary(@PathVariable Integer userId, @RequestParam String content) throws Exception {
        return diaryService.addDiary(userId, content);
    }

    /**
     * 修改目标
     *
     * @param userId  用户id
     * @param id      主键
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/{userId}")
    public Response editDiary(@PathVariable Integer userId,
                              @RequestParam Integer id,
                              @RequestParam String content) throws Exception {
        return diaryService.editDiary(userId, id, content);
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
    public Response deleteDiary(@PathVariable Integer userId,
                                @RequestParam Integer id) throws Exception {
        return diaryService.deleteDiary(userId, id);
    }

    /**
     * 查询用户目标
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public Response findDiaryByUserId(@PathVariable Integer userId) throws Exception {
        return diaryService.findDiaryByUserId(userId);
    }
}
