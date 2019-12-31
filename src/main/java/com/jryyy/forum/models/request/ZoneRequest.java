package com.jryyy.forum.models.request;

import com.jryyy.forum.models.Zone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.utils.ImageUtils;
import com.jryyy.forum.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.Size;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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

    public Zone toUserZone() {
        int msgType = 0;
        if (this.msg != null && this.files == null)
            msgType = 1;
        else if (this.files != null && this.msg == null)
            msgType = 2;
        return Zone.builder().userId(this.userId)
                .msg(this.msg).msgType(msgType).build();
    }

    public List<ZoneImg> saveImage(int id, String uploadFolder, FileUtils fileUtil, ImageUtils imageUtils)
            throws Exception {
        List<ZoneImg> imgs = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileUtil.saveTalkImg(uploadFolder + "zone/image/" + id + "/", file);
            String url = uploadFolder + "zone/image/" + id + "/" + fileName;
            BufferedImage image = ImageIO.read(new FileInputStream(new File(url)));
            ZoneImg z = ZoneImg.builder().dominantColor(imageUtils.dominantColor(image)).
                    width(image.getWidth()).height(image.getHeight()).zoneId(id).
                    imgUrl("zone/image/" + id + "/" + fileName).build();
            imgs.add(z);
        }
        return imgs;
    }
}
