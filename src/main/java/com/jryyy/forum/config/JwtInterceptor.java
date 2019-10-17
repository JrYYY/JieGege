package com.jryyy.forum.config;


import com.jryyy.forum.annotation.UserLoginToken;
import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.BadCredentialsException;
import com.jryyy.forum.models.User;
import com.jryyy.forum.tool.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

//import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenUtils tokenUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constants.USER_TOKEN_STRING);// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                HttpSession session = request.getSession();

//                int userId;
//                try {
//                    userId = (int) session.getAttribute(Constants.USER_ID_STRING);
//                }catch (Exception e){
//                    log.info("没有登入！请登入");
//                    throw new BadCredentialsException("没有登入！请登入");
//                }

                // 执行认证
                if (token == null)
                    throw new BadCredentialsException("无token，请重新登录");
                User user = tokenUtils.decodeJwtToken(token);

                session.setAttribute(Constants.USER_ID_STRING, user.getId());

//                if(user.getId() != userId)
//                    throw new BadCredentialsException("非法访问");

                return true;
            }
        }
        return true;
    }


}
