package com.jryyy.forum.utils.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @User JrYYY
 * @date 2019-07-08 20:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;

    String role() default "{com.jryyy.forum.utils.security.UserLoginToken.role}";
}