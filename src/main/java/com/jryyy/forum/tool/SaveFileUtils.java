package com.jryyy.forum.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaveFileUtils {

    @Autowired
    GenerateFileNameUtils generate;

    public String savePicture(String url, MultipartFile img) {
        generate.isImageType(img.getOriginalFilename());
        try {
            String imgName = generate.duplicateFileName("picture_",
                    generate.interceptSuffix(img.getOriginalFilename()));
            File imgUrl = new File(url);
            if (!imgUrl.exists())
                imgUrl.mkdir();
            img.transferTo(new File(url, imgName));
            return url + imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("图片保存失败");
        }
    }

    public String saveTalkImg(String url, MultipartFile file) {
        try {
            String imgName = generate.duplicateFileName("talkImg",
                    generate.interceptSuffix(file.getOriginalFilename()));
            File saveUrl = new File(url);
            if (!saveUrl.exists())
                saveUrl.mkdir();
            file.transferTo(new File(url, imgName));
            return url + imgName;
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败");
        }
    }

    public List<String> saveTalkImg(String url, MultipartFile[] files) {
        try {
            List<String> imgUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String name = generate.duplicateFileName("zoneImg_",
                        generate.interceptSuffix(file.getOriginalFilename()));
                File saveUrl = new File(url);
                if (!saveUrl.exists())
                    saveUrl.mkdir();
                file.transferTo(new File(url, name));
                imgUrls.add(url + name);
            }
            return imgUrls;
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败");
        }
    }

}
