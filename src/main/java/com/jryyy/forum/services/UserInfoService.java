package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.UserInfoRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户基本信息 服务
 */
public interface UserInfoService {
    /**
     * @param id 邮箱
     * @return {@link com.jryyy.forum.models.response.UserInfoResponse}
     * @throws Exception
     */
    Response viewOtherPeopleSPersonalInformation(Integer id) throws Exception;

    /**
     * 修改背景
     *
     * @param userId 用户id
     * @param img    背景
     * @return {@link Response}
     * @throws Exception
     */
    Response modifyBackgroundImg(int userId, MultipartFile img) throws Exception;

    /**
     * 查询用户信息列表
     *
     * @param value 条件
     * @return {@link com.jryyy.forum.models.response.InfoListResponse}
     * @throws Exception
     */
    Response queryUserList(String value) throws Exception;

    /**
     * 查看用户个人基本信息
     *
     * @param userId 用户id
     * @return {@link com.jryyy.forum.models.response.UserInfoResponse}
     * @throws Exception
     */
    Response viewUserPersonalInformation(int userId) throws Exception;

    /**
     * 更改用户个人基本信息
     *
     * @param request {@link UserInfoRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response changeUserPersonalBasicInformation(UserInfoRequest request) throws Exception;

    /**
     * 签到
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response checkIn(int userId) throws Exception;

    /**
     * 是否签到
     *
     * @param userId 用户id
     * @return true/false
     * @throws Exception
     */
    Response whetherToCheckIn(int userId) throws Exception;
}
