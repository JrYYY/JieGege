package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /* 用户id */
    private int userId;

    /* 用户名称 */
    private String username;

    /* 图像 */
    private String avatarUrl;

    /* 性别 */
    private String sex;

    /* 年龄 */
    private Integer age;
}
