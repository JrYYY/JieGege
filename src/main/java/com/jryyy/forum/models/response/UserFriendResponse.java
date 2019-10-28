package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFriendResponse {
    /* id */
    private Integer id;

    /* 用户id */
    private Integer userId;

    /* 用户名称 */
    private String username;

    /* 头像 */
    private String avatar;

    /* 个性 */
    private String bio;

    /* 最近 */
    private String recent;

    /* 好友添加时间 */
    private Date date;
}
