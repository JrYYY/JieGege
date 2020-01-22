package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.model.request.UserRequest;
import com.jryyy.forum.model.request.UserRequestAccessRequest;
import com.jryyy.forum.model.response.SecurityResponse;

/**
 * 用户操作 服务
 * @author OU
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
