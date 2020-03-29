package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息类
 *
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 图像
     */
    private String avatar;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 签到天数
     */
    private int checkInDays;

    /**
     * 最近签到日期
     */
    private LocalDate checkInDate;

    /**
     * 标签
     */
    private String bio;

    /**
     * 最近登入时间
     */
    private LocalDateTime recentLogin;

    /**
     * 最近状态
     */
    private String recentState;


    /**
     * 联系签到天数
     */
    private Integer continuousDays;

    /**
     * 背景图片
     */
    private String bgImg;

    /**
     * 积分
     */
    private Integer credit;

    /**
     * 追随者数量
     */
    private Integer followers;

    /**
     * 关注数量
     */
    private Integer following;

    /**
     * 喜欢的动态数量
     */
    private Integer likes;
}
