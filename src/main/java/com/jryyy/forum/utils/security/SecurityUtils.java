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

    }



    /**
     * 身份验证
     * @param token     用户令牌
     * @throws GlobalException {@link GlobalStatus}
     */
    public SecurityUtils authenticationToken(String token, TokenUtils tokenUtils) throws GlobalException {
        if (userLoginToken != null) {  }
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


        return this;
    }

    /**
     *  冻结
     *  @return {@link SecurityUtils}
     * @throws GlobalException {@link GlobalStatus}  accountHasBeenFrozen
     */
    public SecurityUtils validationLock(RedisTemplate redisTemplate)throws GlobalException{

        return this;
    }

    /**
     * 验证权限
     * @throws GlobalException {@link GlobalStatus}
     */
    public void validationRole() throws GlobalException{

    }



}
