package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/* 管理员查看用户返回 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminFindUserResponse {
    /*用户id*/
    private int id;

    /* 邮箱 */
    private String email;

    /* 密码 */
    private String pass;

    /* 创建时间 */
    private Date createDate;
}
