package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoListResponse {
    /* id */
    int userId;

    /* 用户名 */
    String username;

    /* 邮箱 */
    String email;

    /* bio */
    String bio;

}
