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

/**
 *
 * @author JrYYY
 */
@Component
public class FileUtils {


    /**
     *
     * @param avatarPath    头像路径
     * @param file          文件
     * @return  路径
     * @throws GlobalException  {@link GlobalStatus}
     */
    public String savePicture(String avatarPath, MultipartFile file) throws GlobalException {
        if (isImageType(Objects.requireNonNull(file.getOriginalFilename()))){
            throw new GlobalException(GlobalStatus.fileTypeIsIncorrect);
        }
        try {
            String imgName = duplicateFileName("picture_",
                    interceptSuffix(file.getOriginalFilename()));
            File imgUrl = new File(avatarPath);
            if (!imgUrl.exists()) {
                imgUrl.mkdirs();
            }
            file.transferTo(new File(avatarPath, imgName));
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
            File saveUrl = new File(avatarPath);
            if (!saveUrl.exists()) {
                saveUrl.mkdirs();
            }
            file.transferTo(new File(avatarPath, imgName));
            return avatarPath + imgName;
        } catch (IOException e) {
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public List<String> saveTalkImg(String avatarPath, MultipartFile[] files) throws GlobalException {
        try {
            List<String> imgUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String name = duplicateFileName(interceptSuffix(Objects.requireNonNull(file.getOriginalFilename())));
                File saveUrl = new File(avatarPath);
                if (!saveUrl.exists()) {
                    saveUrl.mkdirs();
                }
                file.transferTo(new File(avatarPath, name));
                imgUrls.add(name);
            }
            return imgUrls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.imageSaveFailed);
        }
    }

    public void deleteFile(String path) {
        File filePath = new File(path);
        if (filePath.exists()) {
            for (File file : Objects.requireNonNull(filePath.listFiles())) {
                if (file.isDirectory()) {
                    deleteFile(path + "/" + file.getName());
                } else {
                    file.delete();
                }
            }
            filePath.delete();
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
