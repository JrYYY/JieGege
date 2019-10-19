package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneResponse {
    /* id */
    private int id;

    /* 内容 */
    private String msg;

    /* 创建时间 */
    private Date date;

    /* 类型 */
    private int msgType;

    /* 认可 */
    private int approval;

    /* 图片 */
    private List<ZoneImgResponse> zoneImgList;

}

