package com.jryyy.forum.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryZoneRequest {

    /* 每页显示几条数据 */
    @NotBlank(message = "pageSize不能为空")
    private Integer pageSize;

    /* 偏移量 */
    @NotBlank(message = "offset不能为空")
    private Integer offset;
}