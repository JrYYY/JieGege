package com.jryyy.forum.utils.security;


import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import java.lang.reflect.Method;

/**
 * @author 安全工具
 */
@Component
public class SecurityUtils {

    private UserLoginToken userLoginToken;

    private String role;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 拦截携带{@link UserLoginToken}注解的接口
     * @param handlerMethod 接口
     * @return  {@link SecurityUtils}
     */
    public SecurityUtils annotationValidation(HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        Class c = handlerMethod.getBeanType();

        if (method.isAnnotationPresent(PassToken.class)) {
            return this;
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) ||
                c.isAnnotationPresent(UserLoginToken.class)) {
            if (method.isAnnotationPresent(UserLoginToken.class)) {
                this.userLoginToken = method.getAnnotation(UserLoginToken.class);
            }else {
                this.userLoginToken = (UserLoginToken) c.getAnnotation(UserLoginToken.class);
            }
            role = this.userLoginToken.role();
        }
        return this;
    }

    /**
     * 身份验证
     * @param token     用户令牌
     * @param userId    用户id
     * @throws GlobalException  抛出{@link GlobalException}
     */
    public void authenticationToken(String token, String userId) throws GlobalException {
        if (userLoginToken != null) {
            User user = tokenUtils.decodeJwtToken(token);
            if(userId == null || !userId.equals(user.getId().toString())) {
                throw new GlobalException(GlobalStatus.insufficientPermissions);
            } else if(!RoleCode.ALL.equals(role) && !role.equals(user.getRole())) {
                throw new GlobalException(GlobalStatus.insufficientPermissions);
            }
        }
    }
}
