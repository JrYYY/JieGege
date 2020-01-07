package com.jryyy.forum.utils.security;


import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import java.lang.reflect.Method;

/**
 * 安全工具
 * @author JrYYY
 */
public class SecurityUtils {

    /**
     * 拦截对象
     */
    private UserLoginToken userLoginToken;

    private User user;

    public static SecurityUtils create(HandlerMethod handlerMethod){
        return new SecurityUtils(handlerMethod);
    }

    private SecurityUtils(HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        Class c = handlerMethod.getBeanType();
        if (method.isAnnotationPresent(PassToken.class)) {
            return;
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) ||
                c.isAnnotationPresent(UserLoginToken.class)) {
            if (method.isAnnotationPresent(UserLoginToken.class)) {
                userLoginToken = method.getAnnotation(UserLoginToken.class);
            }else {
                userLoginToken = (UserLoginToken) c.getAnnotation(UserLoginToken.class);
            }
        }
    }



    /**
     * 身份验证
     * @param token     用户令牌
     * @throws GlobalException {@link GlobalStatus}
     */
    public SecurityUtils authenticationToken(String token, TokenUtils tokenUtils) throws GlobalException {
        if (userLoginToken != null) { user = tokenUtils.decodeJwtToken(token); }
        return this;
    }

    /**
     * 验证用户
     *
     * @param userId    用户id
     * @return {@link SecurityUtils}
     * @throws GlobalException {@link GlobalStatus}  unauthorizedAccess
     */
    public SecurityUtils validationUser(String userId) throws GlobalException {

        if(userId != null && !userId.equals(user.getId().toString())) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return this;
    }

    /**
     *  冻结
     *  @return {@link SecurityUtils}
     * @throws GlobalException {@link GlobalStatus}  accountHasBeenFrozen
     */
    public SecurityUtils validationLock(RedisTemplate redisTemplate)throws GlobalException{
        if(user != null && !user.getStatus()) {
            throw new GlobalException(GlobalStatus.accountHasBeenFrozen);
//            redisTemplate.opsForValue().get(RedisKey.lockKey())
        }
        return this;
    }

    /**
     * 验证权限
     * @throws GlobalException {@link GlobalStatus}
     */
    public void validationRole() throws GlobalException{
        if(userLoginToken != null && userLoginToken.role().equals(user.getRole())) {
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        }
    }



}
