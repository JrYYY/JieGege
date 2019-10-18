package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 用户空间类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserZone {
    /* id */
    private int id;

    /* 用户id */
    private int userId;

    /* 内容 */
    private String msg;

    /* 创建时间 */
    private Date createDate;

    /* 类型 */
    private int msgType;

    /* 赞同数 */
    private int approval;
}
