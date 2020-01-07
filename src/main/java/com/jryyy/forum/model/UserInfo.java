package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private Integer avatar;

    /* 性别 */
    private String sex;

    /* 年龄 */
    private Integer age;

    /* 签到天数 */
    private int checkInDays;

    /* 最近签到时间 */
    private Date checkInDate;

    /* 标签 */
    private String bio;

    /* 联系签到天数 */
    private Integer continuousDays;

    /* 背景图片 */
    private String bgImg;
}
