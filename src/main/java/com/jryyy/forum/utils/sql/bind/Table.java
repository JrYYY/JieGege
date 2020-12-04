package com.jryyy.forum.utils.sql.bind;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /**
     * 连接表名称
     *
     * @return 表名称
     */
    String value();

    /**
     * 连接表别名
     *
     * @return 表别名
     */
    String tableAlias() default "";
}
