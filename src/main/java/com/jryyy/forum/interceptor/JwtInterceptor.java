package com.jryyy.forum.interceptor;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.User;
import com.jryyy.forum.utils.security.PassToken;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从 http 请求头中取出 token
        String token = request.getHeader(Constants.USER_TOKEN_STRING);

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class c = handlerMethod.getBeanType();

        //PassToken 直接通行
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) ||
                c.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken;

            if (method.isAnnotationPresent(UserLoginToken.class))
                userLoginToken = method.getAnnotation(UserLoginToken.class);
            else
                userLoginToken = (UserLoginToken) c.getAnnotation(UserLoginToken.class);

            if (userLoginToken.required()) {
                HttpSession session = request.getSession();

                // 执行认证
                if (token == null)
                    throw new GlobalException(GlobalStatus.tokenCanNotBeEmpty);

                User user = tokenUtils.decodeJwtToken(token);

                //管理员端口访问权限
                if (userLoginToken.role().equals(RoleCode.ADMIN)) {
                    if (!user.getRole().equals(RoleCode.ADMIN)) {
                        log.error(user.getId() + ":权限不足");
                        throw new GlobalException(GlobalStatus.insufficientPermissions);
                    }
                }

                return true;
            }
        }
        return true;
    }

}