package com.jryyy.forum.controller;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.constant.Template;
import com.jryyy.forum.utils.security.UserRoleCode;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.User;
import com.jryyy.forum.model.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.model.request.UserRequest;
import com.jryyy.forum.model.request.UserRequestAccessRequest;
import com.jryyy.forum.model.response.SecurityResponse;
import com.jryyy.forum.service.UserService;
import com.jryyy.forum.utils.CaptchaUtils;
import com.jryyy.forum.utils.EmailUtils;
import com.jryyy.forum.utils.security.TokenUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 安全验证控制层
 * @author JrYYY
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
    EmailUtils emailUtils;

    @Autowired
    CaptchaUtils captchaUtils;


    /**
     * 登入
     * @param request {@link UserRequest}
     * @return {@link Response}
     */
    @PostMapping("/signIn")
    public Response signIn(@Valid @ModelAttribute UserRequest request) throws Exception {
        return userService.userLogin(request);
    }

    /**
     * @param request   请求
     * @return  {@link SecurityResponse}
     * @throws Exception
     */
    @PostMapping("/login")
    public Response adminLogin(@Valid @ModelAttribute UserRequest request) throws Exception {
        Response response = userService.userLogin(request);
        SecurityResponse securityResponse = (SecurityResponse) response.getData();
        if (!securityResponse.getRole().equals(UserRoleCode.ADMIN)) {
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
     * @return  true|false
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
     * @return token
     * @throws Exception
     */
    @PutMapping("/token")
    public Response putJwt(@RequestParam String token) throws Exception {
        User user = tokenUtils.decodeJwtToken(token);
        Response response = new Response<>(SecurityResponse.builder()
                .token(tokenUtils.createJwtToken(user))
                .role(user.getRole()).userId(user.getId()).build());
        log.info(user.toString() + "---更新token");
        return response;
    }

    /**
     * 注销
     * @param userId 用户id
     * @return {@link Response}
     */
    @UserLoginToken
    @DeleteMapping("/signOut/{userId}")
    public Response signOut(@PathVariable Integer userId) throws Exception {
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
    @PostMapping("/forgotPassword")
    public Response changePassword(@Valid @ModelAttribute ForgotUsernamePasswordRequest request) throws Exception {
        return userService.changePassword(request);
    }

    /**
     * 获取邮箱忘记密码验证码
     * @param email 邮箱
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/forgotPassword")
    public Response passwordCode(@RequestParam String email)throws Exception{
        Response response = userService.verifyUser(email);
        if (!(Boolean) response.getData()) {
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        String content = String.format(Template.MODIFY_PASSWORD_VERIFICATION_TEMPLATE,
                captchaUtils.generateDigitalVerificationCode(RedisKey.modifyPasswordCodeKey(email)));
        emailUtils.sendSimpleMail(email,"忘记密码验证码",content);
        return new Response<>(true);
    }

    /**
     * 生成放送验证码
     * @param email 接受邮箱
     * @return  {@link Response}
     * @throws Exception
     */
    @GetMapping("/generate")
    public Response generateVerificationCode(@RequestParam String email) throws Exception {
        Response response = userService.verifyUser(email);
        if ((Boolean) response.getData()) {
            throw new GlobalException(GlobalStatus.userAlreadyExists);
        }
        String content = String.format(Template.REGISTRATION_VERIFICATION_TEMPLATE,
                captchaUtils.generateDigitalVerificationCode(RedisKey.registrationCodeKey(email)));
        emailUtils.sendSimpleMail(email,"注册验证码",content);
        return new Response<>(true);
    }
}
