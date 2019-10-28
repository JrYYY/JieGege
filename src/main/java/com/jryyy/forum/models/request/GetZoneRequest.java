package com.jryyy.forum.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 请求空间数据 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetZoneRequest {
    /* 总页数 */

    private Integer curPage;

    /* 多少 */

    private Integer pageSize;

    /* 模式 */

    private Integer mode;
}
