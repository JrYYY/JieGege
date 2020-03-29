package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;

/**
 * 记忆单词服务
 *
 * @author JrYYY
 */
public interface WordService {

    /**
     * 获取单词库
     *
     * @param userId 用户
     * @return {@link Response}
     * @throws Exception
     */
    Response getWordLibrary(Integer userId) throws Exception;

    /**
     * 记忆
     *
     * @param userId        用户id
     * @param understanding 认识
     * @return {@link com.jryyy.forum.model.Word}
     * @throws Exception
     */
    Response memory(Integer userId, Boolean understanding) throws Exception;

    /**
     * 复习
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response review(Integer userId) throws Exception;

    /**
     * 清除
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response remove(Integer userId) throws Exception;
}
