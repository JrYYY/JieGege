package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFriendResponse {
    /* id */
    private Integer id;

    /* 用户名称 */
    private String username;

    /* 头像 */
    private String avatarUrl;

    /* 好友添加时间 */
    private String date;
}
