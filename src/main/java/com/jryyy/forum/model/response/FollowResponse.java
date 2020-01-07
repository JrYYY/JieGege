package com.jryyy.forum.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 关注|关注者 响应类
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponse {
    /** id */
    private Integer id;

    /** 用户id */
    private Integer userId;

    /** 用户名称 */
    private String username;

    /** 头像 */
    private String avatar;

    /** 个性 */
    private String bio;

    /** 最近 */
    private String recent;

    /** 好友添加时间 */
    private Date date;
}
