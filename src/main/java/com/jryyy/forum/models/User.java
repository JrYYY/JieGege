package com.jryyy.forum.models;

import lombok.*;

/**
 * 用户基础类
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /* 用户id */
    private Integer id;

    /* 用户登入账号 */
    private String emailName;

    /* 用户登入密码 */
    private String password;

    /* 用户权限 */
    private String role;

    /* 用户状态 */
    private String status;

    /* 登入失败尝试次数 */
    private Integer loginFailedAttemptCount;

    /* 创建时间 */
    private String createDate;

}
