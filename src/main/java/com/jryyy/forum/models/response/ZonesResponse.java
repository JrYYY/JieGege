package com.jryyy.forum.models.response;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.request.GetZoneRequest;
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
public class ZonesResponse {


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
    private List<ZoneResponse> zones;


    public static ZonesResponse create(UserZoneMapper userZoneMapper, GetZoneRequest request) throws Exception {
        int totalNum = userZoneMapper.countZoneNum();
        int totalPageNum = (int) Math.ceil((float) totalNum / request.getPageSize());
        if (!(request.getCurPage() > 0 && request.getCurPage() <= totalPageNum)) {
            throw new GlobalException(GlobalStatus.noSuchPage);
        }
        return ZonesResponse.builder()
                .curPageNumber(request.getCurPage())
                .numberPerPage(request.getPageSize())
                .totalNum(totalNum).totalPageNum(totalPageNum)
                .hasNext(request.getCurPage() != totalPageNum)
                .hasPrev(request.getCurPage() != 1)
                .build();

    }

}
