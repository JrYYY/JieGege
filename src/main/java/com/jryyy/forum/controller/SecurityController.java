package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.User;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;
import com.jryyy.forum.models.response.SecurityResponse;
import com.jryyy.forum.services.UserService;
import com.jryyy.forum.utils.CaptchaUtils;
import com.jryyy.forum.utils.security.TokenUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 安全验证控制层
 */
@Slf4j
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    CaptchaUtils codeMailUtil;

    /**
     * 登入
     *
     * @param request {@link UserRequest}
     * @return {@link Response}
     */
    @PostMapping("/signIn")
    public Response signIn(@Valid @RequestBody UserRequest request) throws Exception {
        return userService.userLogin(request);
    }

    /**
     * @param request   请求
     * @return  {@link SecurityResponse}
     * @throws Exception
     */
    @PostMapping("/login")
    public Response adminLogin(@RequestBody @Valid UserRequest request) throws Exception {
        Response response = userService.userLogin(request);
        SecurityResponse userRequest = (SecurityResponse) response.getData();
        if (!userRequest.getPower().equals(RoleCode.ADMIN)) {
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        }
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
     * 验证邮箱是否存在
     * @param email 邮箱
     * @return
     * @throws Exception
     */
    @GetMapping("/signUp")
    public Response verification(@RequestParam String email) throws Exception {
        return userService.verifyUser(email);
    }

    /**
     * 更新token
     *
     * @param token   jwt加密码
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/token")
    public Response putJwt(@RequestParam String token) throws Exception {
        User user = tokenUtils.decodeJwtToken(token);
        Response response = new Response<>(SecurityResponse.builder()
                .token(tokenUtils.createJwtToken(user))
                .power(user.getRole()).build());
        log.info(user.toString() + "---更新token");
        return response;
    }

    /**
     * 注销
     *
     * @param userId 用户id
     * @return {@link Response}
     */
    @UserLoginToken
    @DeleteMapping("/signOut")
    public Response signOut(@RequestParam Integer userId) throws Exception {
        tokenUtils.deleteJwtToken(userId);
        log.info(userId + ": 注销成功");
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
    public Response changePassword(@Valid @RequestBody ForgotUsernamePasswordRequest request) throws Exception {
        return userService.changePassword(request);
    }

    @GetMapping("/generate")
    public Response generateVerificationCode(@RequestParam String email) throws Exception {
        Response response = userService.verifyUser(email);
        if ((Boolean) response.getData()) {
            throw new GlobalException(GlobalStatus.userAlreadyExists);
        }
        return new Response<>(true);
    }
}
