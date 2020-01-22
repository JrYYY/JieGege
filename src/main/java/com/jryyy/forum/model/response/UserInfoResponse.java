package com.jryyy.forum.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    /**
     * id
     */
    private int userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * bio
     */
    private String bio;

    /**
     * 头像
     */
    private String avatar;

}
