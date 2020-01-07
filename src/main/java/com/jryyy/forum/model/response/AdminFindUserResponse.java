package com.jryyy.forum.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 *  管理员查看用户返回
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminFindUserResponse {

    /**用户id*/
    private int id;

    /** 邮箱 */
    private String email;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String pass;

    /** 创建时间 */
    private Date createDate;
}
