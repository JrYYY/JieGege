package com.jryyy.forum.tool.file;

import org.springframework.stereotype.Component;

@Component
public class GenerateFileNameUtils {

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
