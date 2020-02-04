package com.jryyy.forum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *  请求空间数据
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetZoneRequest {
    /**
     * 用户id
     */
    private Integer id;

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
    @Min(value = 1,message = "非法访问")
    @Max(value = 2,message = "非法访问")
    @NotNull(message = "模式不能为空")
    private Integer mode;
}
