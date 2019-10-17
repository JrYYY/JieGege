package com.jryyy.forum.tool.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class SaveFileUtils {

    @Autowired
    GenerateFileNameUtils generate;

    public String savePicture(String url, int id, MultipartFile img) {
        generate.isImageType(img.getOriginalFilename());
        try {
            String imgName = generate.duplicateFileName("picture_" + id + "_",
                    generate.interceptSuffix(img.getOriginalFilename()));
            File imgUrl = new File(url);
            if (!imgUrl.exists())
                imgUrl.mkdir();
            img.transferTo(new File(url, imgName));
            return imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("图片保存失败");
        }
    }

    public String saveTalkImg(String url, MultipartFile file) {
        try {
            String imgName = generate.duplicateFileName("talkImg",
                    generate.interceptSuffix(file.getOriginalFilename()));
            File saveImg = new File(url, imgName);
            if (!saveImg.getParentFile().exists())
                saveImg.getParentFile().mkdir();
            file.transferTo(saveImg);
            return imgName;
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败");
        }
    }

}
