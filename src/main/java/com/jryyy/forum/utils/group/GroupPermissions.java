package com.jryyy.forum.utils.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author OU
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupPermissions {
    String permission() default GroupRoleCode.ALL;
    String[] notAllowed() default {};
}
