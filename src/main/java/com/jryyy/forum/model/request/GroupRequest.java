package com.jryyy.forum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 创建群组请求类
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {

    @NotNull(message = "请填写群名称")
    private String name;

    private String slogan;

    @NotNull(message = "创建者id不能为空")
    private Integer founderId;

    @Size(min = 1,message = "您的群组没有成员,无法组建群组")
    private Integer[] members;
}
