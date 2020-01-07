package com.jryyy.forum.config.interceptor;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.utils.security.SecurityUtils;
import com.jryyy.forum.utils.security.TokenUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JrYYY
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

     private static final String USER_ID_STRING = "userId";

     private static final String USER_ID_REGEX = "\\d+$";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader(Constants.USER_TOKEN_STRING);
        String userId = request.getParameter(USER_ID_STRING);
        Matcher matcher = Pattern.compile(USER_ID_REGEX).matcher(request.getRequestURI());
        if(matcher.find()){
            userId = matcher.group();
        }
        SecurityUtils.create((HandlerMethod)handler).authenticationToken(token,tokenUtils).
                validationUser(userId).validationLock(redisTemplate).validationRole();

        return true;
    }

}
