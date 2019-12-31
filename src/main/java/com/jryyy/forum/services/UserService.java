package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;
import com.jryyy.forum.models.response.SecurityResponse;

/**
 * 用户操作 服务
 */
public interface UserService {
    /**
     * 用户登录
     * @param request {@link UserRequest}
     * @return {@link SecurityResponse}
     * @throws Exception
     */
    Response userLogin(UserRequest request) throws Exception;

    /**
     * 用户注册
     *
     * @param request {@link UserRequestAccessRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response userRegistration(UserRequestAccessRequest request) throws Exception;

    /**
     * 验证用户是否存在
     *
     * @param email 邮箱
     * @return true/false
     * @throws Exception
     */
    Response verifyUser(String email) throws Exception;

    /**
     * 忘记密码修改密码
     * @param request {@link ForgotUsernamePasswordRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response changePassword(ForgotUsernamePasswordRequest request) throws Exception;


}
