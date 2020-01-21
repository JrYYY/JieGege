package com.jryyy.forum.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author OU
 */
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


    private String sex;

    @Min(value = 0, message = "年龄输入不规范")
    private Integer age;

    /** 标签 */
    @Size(max = 191)
    private String bio;

}
