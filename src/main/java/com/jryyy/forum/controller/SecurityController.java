package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.User;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;
import com.jryyy.forum.models.response.UserResponse;
import com.jryyy.forum.services.UserService;
import com.jryyy.forum.tool.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 安全验证控制层
 */
@RestController
@RequestMapping("/security")
@Slf4j
public class SecurityController {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    /**
     * 登入
     *
     * @param request {@link UserRequest}
     * @return {@link Response}
     */
    @PostMapping("/signIn")
    public Response signIn(@Valid @ModelAttribute UserRequest request, HttpSession session) throws Exception {
        Response<UserResponse> response = userService.userLogin(request);
        session.setAttribute(Constants.USER_ID_STRING, tokenUtils.decodeJwtToken(response.getData().getToken()).getId());
        log.info(request.toString() + "__________登入成功");
        return response;
    }

    /**
     * 注册
     *
     * @param request {@link UserRequest}
     * @return {@link Response}
     */
    @PostMapping("/signUp")
    public Response signUp(@Valid @ModelAttribute UserRequestAccessRequest request) throws Exception {
        log.info(request.toString());
        return userService.userRegistration(request);
    }

    /**
     * 更新token
     *
     * @param token   jwt加密码
     * @param session {@link HttpSession}
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/token")
    public Response PutJWT(String token, HttpSession session) throws Exception {
        User user = tokenUtils.decodeJwtToken(token);
        Response response = userService.
                userLogin(UserRequest.builder().
                        name(user.getEmailName()).
                        pass(user.getPassword()).
                        build());
        session.setAttribute(Constants.USER_ID_STRING, user.getId());
        log.info(user.toString() + "---更新token");
        return response;
    }

    /**
     * 注销
     *
     * @param session {@link HttpSession}
     * @return
     */
    @DeleteMapping("/token")
    public Response Logout(HttpSession session) {
        session.removeAttribute(Constants.USER_ID_STRING);
        log.info("注销成功");
        return new Response();
    }

    /**
     * 修改密码
     *
     * @param request {@link UserRequest}
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping(value = "/forgotPassword")
    public Response changePassword(@Valid @ModelAttribute ForgotUsernamePasswordRequest request) throws Exception {
        return userService.changePassword(request);
    }


}
