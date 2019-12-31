package com.jryyy.forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Date;

/**
 * 用户基础类
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends IdentifiableEntity{

    /** 用户登入账号 */
    private String emailName;

    /** 用户登入密码 */
    @JsonIgnore
    private String password;

    /** 用户权限 */
    private String role;

    /** 用户状态 */
    private Boolean status;

    /** 登入失败尝试次数 */
    @JsonIgnore
    private Integer loginFailedAttemptCount;

    /** 创建时间 */
    private Date createDate;

}
