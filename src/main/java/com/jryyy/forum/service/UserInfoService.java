package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.UserInfoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author OU
 */
public interface UserInfoService {

    /**
     * 查看用户信息
     * @param userId   用户id
     * @return  {@link com.jryyy.forum.model.UserInfo}
     * @throws Exception
     */
    Response viewUserInfo(Integer userId)throws Exception;

    /**
     * 更改用户信息
     * @param request   {@link UserInfoRequest}
     * @return  {@link Response}
     * @throws Exception
     */
    Response changeUserInfo(UserInfoRequest request)throws Exception;

    /**
     * 更具信息查找用户
     * @param info  信息
     * @return  {@link }
     * @throws Exception
     */
    Response searchUser(String info)throws Exception;

    /**
     * 更新用户头像
     * @param userId    用户id
     * @param avatar    头像
     * @return  {@link Response}
     * @throws Exception
     */
    Response updateAvatar(Integer userId, MultipartFile avatar)throws Exception;

    /**
     *
     * @param userId
     * @param bgImg
     * @return
     * @throws Exception
     */
    Response updateBgImg(Integer userId,MultipartFile bgImg)throws Exception;

    /**
     * 签到
     * @param userId    用户id
     * @return  {@link Response}
     * @throws Exception
     */
    Response checkIn(Integer userId)throws Exception;
}
