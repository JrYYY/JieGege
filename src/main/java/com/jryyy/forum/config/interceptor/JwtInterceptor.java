package com.jryyy.forum.config.interceptor;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import com.jryyy.forum.utils.security.PassToken;
import com.jryyy.forum.utils.security.SecurityUtils;
import com.jryyy.forum.utils.security.TokenUtils;

import com.jryyy.forum.utils.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod)handler).getMethod();
        Class c = ((HandlerMethod)handler).getBeanType();
        //检查有没有需要用户权限的注解
        try {
            if (method.isAnnotationPresent(UserLoginToken.class) || c.isAnnotationPresent(UserLoginToken.class)) {
                UserLoginToken userLoginToken;
                if (method.isAnnotationPresent(UserLoginToken.class)) {
                    userLoginToken = method.getAnnotation(UserLoginToken.class);
                } else {
                    userLoginToken = (UserLoginToken) c.getAnnotation(UserLoginToken.class);
                }
                // 从 http 请求头中取出 token
                String token = request.getHeader(Constants.USER_TOKEN_STRING);

                //从路由中提取id
                String userId = request.getParameter(USER_ID_STRING);
                Matcher matcher = Pattern.compile(USER_ID_REGEX).matcher(request.getRequestURI());
                if (matcher.find()) {
                    userId = matcher.group();
                }

                User user = tokenUtils.decodeJwtToken(token);
                if (userId != null && !userId.equals(user.getId().toString())) {
                    throw new GlobalException(GlobalStatus.unauthorizedAccess);
                }

                if (user != null && !user.getStatus()) {
                    throw new GlobalException(GlobalStatus.accountHasBeenFrozen);
                }

                if (user != null && userLoginToken.role().equals(user.getRole())) {
                    throw new GlobalException(GlobalStatus.insufficientPermissions);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return true;
    }

}
