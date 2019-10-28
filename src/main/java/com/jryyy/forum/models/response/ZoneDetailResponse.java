package com.jryyy.forum.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class ZoneDetailResponse extends ZoneResponse {

    @Setter
    @Getter
    private List<ZonePraiseResponse> Users;

    public ZoneDetailResponse(ZoneResponse zone) {
        super(zone.getId(), zone.getUserId(), zone.getEmail(), zone.getMsg(), zone.getDate(), zone.getMsgType(), zone.getPraise(), zone.getZoneImgList());
    }

}
