package com.jryyy.forum.config.interceptor;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.User;
import com.jryyy.forum.utils.security.PassToken;
import com.jryyy.forum.utils.security.SecurityUtils;
import com.jryyy.forum.utils.security.TokenUtils;
import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author JrYYY
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    SecurityUtils securityUtils;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从 http 请求头中取出 token
        String token = request.getHeader(Constants.USER_TOKEN_STRING);
        String userId = request.getParameter(Constants.USER_ID_STRING);
        securityUtils.annotationValidation((HandlerMethod) handler).authenticationToken(token,userId);
        return true;
    }

}
