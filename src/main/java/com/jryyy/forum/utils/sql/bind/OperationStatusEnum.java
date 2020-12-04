package com.jryyy.forum.utils.sql.bind;

/**
 * sql 条件关系
 *
 * @author ou
 */
public enum OperationStatusEnum {
    EQUAL("等于", "="),
    DIFFERENCE("不等于", "!="),
    MORE_THAN("大于", ">"),
    LESS_THAN("小于", "<"),
    MORE_THAN_AND_EQUAL("小于等于", ">="),
    LESS_THAN_AND_EQUAL("大于等于", "<="),
    LINK("模糊查询", "like"),
    IN("包含", "in"),
    NOT_IN("不包含", "not in"),
    IS_NULL("空", "is null"),
    NOT_IS_NULL("非空", "not is null");

    private String msg;

    private String code;


    OperationStatusEnum(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
