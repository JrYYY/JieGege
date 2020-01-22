package com.jryyy.forum.utils;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件操作工具实例
 * @author JrYYY
 */
@Slf4j
@Component
public class FileUtils {

    /** 基本路径 */
    @Value("${file.uploadFolder}")
    private String uploadFolder;


    /**
     *
     * @param avatarPath    头像路径
     * @param file          文件
     * @return  路径
     * @throws GlobalException  {@link GlobalStatus}
     */
    public String savePicture(String avatarPath, MultipartFile file) throws GlobalException {
        try {
            String imgName = duplicateFileName("picture_",
                    interceptSuffix(file.getOriginalFilename()));
            File imgUrl = new File(uploadFolder+avatarPath);
            if (!imgUrl.exists()) {
                imgUrl.mkdirs();
            }
            file.transferTo(new File(uploadFolder+avatarPath, imgName));
            return avatarPath + imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public String saveTalkImg(String avatarPath, MultipartFile file) throws GlobalException {
        try {
            String imgName = duplicateFileName("zoneImg_",
                    interceptSuffix(Objects.requireNonNull(file.getOriginalFilename())));
            File saveUrl = new File(uploadFolder+avatarPath);
            if (!saveUrl.exists()) {
                saveUrl.mkdirs();
            }
            file.transferTo(new File(uploadFolder+avatarPath, imgName));
            return avatarPath + imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public List<String> saveTalkImg(String avatarPath, MultipartFile[] files) throws GlobalException {
        try {
            List<String> imgUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String name = duplicateFileName(interceptSuffix(Objects.requireNonNull(file.getOriginalFilename())));
                File saveUrl = new File(uploadFolder+avatarPath);
                if (!saveUrl.exists()) {
                    saveUrl.mkdirs();
                }
                file.transferTo(new File(uploadFolder+avatarPath, name));
                imgUrls.add(name);
            }
            return imgUrls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }



    /**
     *  判单文件类型
     * @param fileName  文件名
     * @return  判断
     */
    private boolean isImageType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        return type.toUpperCase().equals("PNG") || type.toUpperCase().equals("JPG") ||
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
