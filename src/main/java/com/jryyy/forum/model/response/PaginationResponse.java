package com.jryyy.forum.model.response;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询空间返回类
 * @author OU
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse<T> {

    /** 当前第几页 */
    private int curPageNumber;

    /** 是否有下一页*/
    private boolean hasNext;

    /** 是否有上一页*/
    private boolean hasPrev;

    /** 每页多少 */
    private int numberPerPage;

    /** 总数量 */
    private int totalNum;

    /** 总共页数*/
    private int totalPageNum;

    /** 空间列表 */
    private T pageData;


    public PaginationResponse(T pageData, int totalNum, Integer curPage, Integer pageSize)throws Exception{
        int totalPageNum = (int) Math.ceil((float) totalNum / pageSize);
        if (!(curPage > 0 && curPage <= totalPageNum+1)) {
            throw new GlobalException(GlobalStatus.noSuchPage); }
        this.curPageNumber = curPage;
        this.numberPerPage = pageSize;
        this.totalNum = totalNum;
        this.totalPageNum = totalPageNum;
        this.hasNext = curPage != totalPageNum && totalPageNum != 0;
        this.hasPrev = curPage != 1;
        this.pageData = pageData;
    }

}
