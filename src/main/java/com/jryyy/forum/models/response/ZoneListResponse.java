package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询空间返回类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZoneListResponse {


    /* 当前第几页 */
    private int curPageNumber;

    /*是否有下一页*/
    private boolean hasNext;

    /*是否有上一页*/
    private boolean hasPrev;

    /* 每页多少 */
    private int numberPerPage;

    /* 总数量 */
    private int totalNum;

    /* 总共页数*/
    private int totalPageNum;

    /* 空间列表 */
    private List<ZoneResponse> zones;

}
