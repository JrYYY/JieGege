package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    /* token 加密码 */
    private String token;
    /* 权限 */
    private String power;

    /* 粉丝人数 */
    private int followersNumber;

    /* 关注人数 */
    private int followingNumber;
}
