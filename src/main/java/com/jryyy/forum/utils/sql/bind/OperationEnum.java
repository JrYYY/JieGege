package com.jryyy.forum.utils.sql.bind;

/**
 * sql 条件关系
 *
 * @author ou
 */
public enum OperationEnum {
    DEFAULT("默认", "", 0),
    EQUAL("等于", "=", 1),
    DIFFERENCE("不等于", "!=", 1),
    MORE_THAN("大于", ">", 1),
    LESS_THAN("小于", "<", 1),
    MORE_THAN_AND_EQUAL("小于等于", ">=", 1),
    LESS_THAN_AND_EQUAL("大于等于", "<=", 1),
    LIKE("模糊查询", "LIKE", 1),
    LEFT_LIKE("左模糊查询", "LIKE", 1),
    RIGHT_LIKE("有模糊查询", "LIKE", 1),

    //弃用
    //IN("包含", "in",2),
    //NOT_IN("不包含", "not in",2),

    IS_NULL("是空", "is null", 0),
    IS_NOT_NULL("非空", "is not null", 0);

    /**
     * 功能描述
     */
    private String msg;

    /**
     * 数据状态 0：不需要，1：单个，2：多个
     */
    private Integer code;

    /**
     * 功能
     */
    private String sql;

    OperationEnum(String msg, String sql, Integer code) {
        this.msg = msg;
        this.sql = sql;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getSql() {
        return sql;
    }
}
