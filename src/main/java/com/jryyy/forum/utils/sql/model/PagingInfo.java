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

    public int getCurPageNumber() {
        return curPageNumber;
    }

    public void setCurPageNumber(int curPageNumber) {
        this.curPageNumber = curPageNumber;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(int numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
