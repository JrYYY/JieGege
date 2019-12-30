package com.jryyy.forum.models.request;

import com.jryyy.forum.models.UserZone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.utils.DominantColorUtils;
import com.jryyy.forum.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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
    @Size(max = 4, message = "最多上传4张图片")
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

    public List<ZoneImg> toZoneImg(int id, String uploadFolder, FileUtils fileUtil, DominantColorUtils dominantColorUtils)
            throws Exception {
        List<ZoneImg> imgs = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileUtil.saveTalkImg(uploadFolder + "zone/image/" + id + "/", file);
            String url = uploadFolder + "zone/image/" + id + "/" + fileName;
            ZoneImg z = dominantColorUtils.dominantColor(url);
            z.setZoneId(id);
            z.setImgUrl("zone/image/" + id + "/" + fileName);
            imgs.add(z);
        }
        return imgs;
    }
}
