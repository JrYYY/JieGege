package com.jryyy.forum.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/* 请求空间数据 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetZoneRequest {
    /**
     * 总页数
     */
    @Min(value = 0, message = "每页小于0的页面")
    @NotNull(message = "不能为空")
    private Integer curPage;

    /**
     * 多少
     */
    @Min(value = 0, message = "每页不能小于0")
    @NotNull(message = "不能为空")
    private Integer pageSize;

    /**
     * 模式
     */
    private Integer mode = 0;
}
