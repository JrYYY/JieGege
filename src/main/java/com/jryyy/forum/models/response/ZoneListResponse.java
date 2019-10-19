package com.jryyy.forum.models.response;

import java.util.List;

public class ZoneListResponse {
    Integer pageCount;      //共有多少页
    Integer totalCount;     //共有多少条数据
    Integer currentPage;    //当前是第几页
    /* 空间列表 */
    private List<ZoneResponse> zones;

}
