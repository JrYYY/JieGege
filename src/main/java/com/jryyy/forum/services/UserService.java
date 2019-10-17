package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;

public interface UserService {
    /**
     * 用户登录
     *
     * @param request {@link UserRequest}
     * @return {@link Response}
     */
    Response userLogin(UserRequest request) throws Exception;

    /**
     * 用户注册
     *
     * @param request {@link UserRequestAccessRequest}
     * @return {@link Response}
     */
    Response userRegistration(UserRequestAccessRequest request) throws Exception;

    /**
     * 修改密码
     *
     * @param request {@link ForgotUsernamePasswordRequest}
     * @return {@link Response}
     */
    Response changePassword(ForgotUsernamePasswordRequest request) throws Exception;

}
