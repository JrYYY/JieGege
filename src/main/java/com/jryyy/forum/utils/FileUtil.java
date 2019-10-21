package com.jryyy.forum.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FileUtil {

    @Autowired
    GenerateFileNameUtil generate;

    public String savePicture(String url, MultipartFile img) {
        generate.isImageType(img.getOriginalFilename());
        try {
            String imgName = generate.duplicateFileName("picture_",
                    generate.interceptSuffix(img.getOriginalFilename()));
            File imgUrl = new File(url);
            if (!imgUrl.exists())
                imgUrl.mkdirs();
            img.transferTo(new File(url, imgName));
            return imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("图片保存失败");
        }
    }

    public String saveTalkImg(String url, MultipartFile file) {
        try {
            String imgName = generate.duplicateFileName("zoneImg_",
                    generate.interceptSuffix(file.getOriginalFilename()));
            File saveUrl = new File(url);
            if (!saveUrl.exists())
                saveUrl.mkdirs();
            file.transferTo(new File(url, imgName));
            return imgName;
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
                if (!saveUrl.exists()) {
                    saveUrl.mkdirs();
                }
                file.transferTo(new File(url, name));
                imgUrls.add(name);
            }
            return imgUrls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件保存失败");
        }
    }

    public void deleteFile(String path) {
        File url = new File(path);
        if (url.exists()) {
            for (File file : Objects.requireNonNull(url.listFiles())) {
                if (file.isDirectory()) {
                    deleteFile(path + "/" + file.getName());
                } else {
                    file.delete();
                }
            }
            url.delete();
        }
    }

}
