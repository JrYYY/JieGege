package com.jryyy.forum.utils.sql.model;

public class PagingInfo {

    /**
     * 当前第几页
     */
    private int curPageNumber;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否有上一页
     */
    private boolean hasPrev;

    /**
     * 每页多少
     */
    private int numberPerPage;

    /**
     * 总数量
     */
    private int totalNum;

    /**
     * 总共页数
     */
    private int totalPageNum;

}
