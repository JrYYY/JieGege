package com.jryyy.forum.utils.sql.bind;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Condition {

    /**
     * @return 条件对象
     */
    String value() default "";

    /**
     * 默认运算关系 =
     *
     * @return 运算关系
     */
    OperationEnum operation() default OperationEnum.DEFAULT;

    /**
     * 是否不能为空
     *
     * @return true / false
     */
    boolean notNull() default false;
}
