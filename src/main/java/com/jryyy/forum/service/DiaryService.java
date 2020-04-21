package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 日记服务
 *
 * @author OU
 */
public interface DiaryService {

    /**
     * 添加目标
     *
     * @param userId  用户id
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    Response addDiary(Integer userId, String content) throws Exception;

    /**
     * 修改目标
     *
     * @param userId  用户id
     * @param id      主键
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    Response editDiary(Integer userId, Integer id, String content) throws Exception;

    /**
     * 删除目标
     *
     * @param userId 用户id
     * @param id     主键
     * @return {@link Response}
     * @throws Exception
     */
    Response deleteDiary(Integer userId, Integer id) throws Exception;

    /**
     * 查询用户目标
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response findDiaryByUserId(Integer userId) throws Exception;
}
