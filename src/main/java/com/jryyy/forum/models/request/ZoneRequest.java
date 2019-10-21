package com.jryyy.forum.models.request;

import com.jryyy.forum.models.UserZone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 空间请求类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRequest {
    /* 用户id */
    private int userId;

    /* 内容 */
    private String msg;

    /* 图片 */
    private MultipartFile[] files;

    public UserZone toUserZone() {
        int msgType = 0;
        if (this.msg != null && this.files == null)
            msgType = 1;
        else if (this.files != null && this.msg == null)
            msgType = 2;
        return UserZone.builder().userId(this.userId)
                .msg(this.msg).msgType(msgType).build();
    }
}
