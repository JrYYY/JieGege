package com.jryyy.forum.model.request;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Zone;
import com.jryyy.forum.model.ZoneImg;
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
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRequest  {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 内容
     */
    private String msg;

    /**
     * 图片
     */
    @Size(max = 4, message = "最多上传4张图片")
    private MultipartFile[] files;


    public Zone getZone() throws Exception{
        int msgType = 0;
        if (this.msg != null && this.files == null) {
            msgType = 1;
        }
        if (this.files != null && this.msg == null) {
            msgType = 2;
        }
        if(this.files == null && this.msg == null) {
            throw new GlobalException(GlobalStatus.noContent);
        }
        return Zone.builder().userId(this.userId).msg(this.msg).msgType(msgType).build();
    }

    public List<ZoneImg> saveImage(int id, String uploadFolder, FileUtils fileUtil, ImageUtils imageUtils)
            throws Exception {
        List<ZoneImg> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileUtil.saveTalkImg(KayOrUrl.zoneImageUrl(userId), file);
            String url = uploadFolder + fileName;
            BufferedImage image = ImageIO.read(new FileInputStream(new File(url)));
            images.add(ZoneImg.builder().dominantColor(imageUtils.dominantColor(image)).
                    width(image.getWidth()).height(image.getHeight()).zoneId(id).
                    imgUrl(fileName).build());
        }
        return images;
    }

}
