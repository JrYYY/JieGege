package com.jryyy.forum.utils;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FileUtils {


    public String savePicture(String url, MultipartFile img) throws GlobalException {
        isImageType(img.getOriginalFilename());
        try {
            String imgName = duplicateFileName("picture_",
                    interceptSuffix(img.getOriginalFilename()));
            File imgUrl = new File(url);
            if (!imgUrl.exists())
                imgUrl.mkdirs();
            img.transferTo(new File(url, imgName));
            return imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public String saveTalkImg(String url, MultipartFile file) throws GlobalException {
        try {
            String imgName = duplicateFileName("zoneImg_",
                    interceptSuffix(file.getOriginalFilename()));
            File saveUrl = new File(url);
            if (!saveUrl.exists())
                saveUrl.mkdirs();
            file.transferTo(new File(url, imgName));
            return imgName;
        } catch (IOException e) {
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public List<String> saveTalkImg(String url, MultipartFile[] files) throws GlobalException {
        try {
            List<String> imgUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String name = duplicateFileName("zoneImg_",
                        interceptSuffix(file.getOriginalFilename()));
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
            throw new GlobalException(GlobalStatus.imageSaveFailed);
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

    public boolean isImageType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        return type.toUpperCase().equals("PNG") ||
                type.toUpperCase().equals("JPG") ||
                type.toUpperCase().equals("GIF");
    }

    public String interceptSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String duplicateFileName(String suffixName) {
        return System.currentTimeMillis() + suffixName;
    }

    public String duplicateFileName(String head, String suffixName) {
        return head + System.currentTimeMillis() + suffixName;
    }

    public String duplicateFileName(String head, String tail, String suffixName) {
        return head + System.currentTimeMillis() + tail + suffixName;
    }


}
