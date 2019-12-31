package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  用户登入响应类
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityResponse {
    /**
     * token 加密码
     */
    private String token;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限
     */
    private String power;
}
