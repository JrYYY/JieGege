package com.jryyy.forum.models.request;

import com.jryyy.forum.models.UserZone;

public class UserZoneRequest {
    /* 用户id */
    private int userId;

    /* 内容 */
    private String msg;

    /* 类型 */
    private int msgType;

    /* 图片 */
    private String[] zoneImg;

    public UserZone toUserZone() {
        return UserZone.builder()
                .userId(userId).msg(msg)
                .msgType(msgType).build();
    }
}
