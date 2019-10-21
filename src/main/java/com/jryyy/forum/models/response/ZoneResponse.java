package com.jryyy.forum.models.response;

import com.jryyy.forum.dao.UserZoneMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private int praise;

    /* 图片 */
    private List<ZoneImgResponse> zoneImgList;

    public void toZoneImgList(UserZoneMapper zoneMapper, String file_url) throws Exception {
        this.zoneImgList = zoneMapper.
                findAllZoneImgByZoneId(this.id);
        for (ZoneImgResponse img : this.zoneImgList)
            img.setImgUrl(file_url + img.getImgUrl());
    }

}

