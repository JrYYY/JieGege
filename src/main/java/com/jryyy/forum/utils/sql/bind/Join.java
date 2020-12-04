package com.jryyy.forum.utils.sql.bind;

import java.lang.annotation.*;

/**
 * join sql
 *
 * @author OU
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Join {
    /**
     * 连接表名称
     *
     * @return 表名称
     */
    String[] value();

    /**
     * @return 连接方式
     */
    JoinTypeEnum[] joinType() default JoinTypeEnum.JOIN;
}
