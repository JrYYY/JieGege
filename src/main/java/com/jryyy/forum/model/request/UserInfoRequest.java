package com.jryyy.forum.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoRequest {

    private final static String USERNAME_REGULAR = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
    private final static String SEX_REGULAR = "/^(男|女)$/";

    private Integer userId;

    @Pattern(regexp = USERNAME_REGULAR, message = "用户名输入不规范")
    private String username;

    @Min(value = 0, message = "头像导入错误")
    @Max(value = 64, message = "头像导入错误")
    private Integer avatar;

    //@Pattern(regexp = SEX_REGULAR,message = "输入性别不规范")
    private String sex;

    @Min(value = 0, message = "年龄输入不规范")
    private Integer age;

    /* 标签 */
    @Size(max = 191)
    private String bio;

}
