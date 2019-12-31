package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 用户信息
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    /** 用户id */
    private Integer userId;

    /** 邮箱 */
    private String email;

    /** 用户名称 */
    private String username;

    /** 图像 */
    private Integer avatar;

    /** 性别 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 关注数 */
    private Integer followingNum;

    /** 粉丝数 */
    private Integer followersNum;

    /** 签到天数 */
    private int checkInDays;

    /** 最近签到时间 */
    private Date checkInDate;

    /** 标签 */
    private String bio;

    /** 上传数量 */
    private Integer zoneNum;

    /** 联系签到天数 */
    private Integer continuousDays;

    /** 背景 */
    private String bgImg;
}
