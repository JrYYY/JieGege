package com.jryyy.forum.utils.sql.bind;

import java.lang.annotation.*;

/**
 * 标记表明
 *
 * @author OU
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    /**
     * 例： a.user 或者 user ;如果使用默认将引用元素名称
     *
     * @return 列名 | 表明.列名
     */
    String value() default "";
}
