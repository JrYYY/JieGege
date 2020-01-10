package com.jryyy.forum.utils;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * 提出图片主题色
 * @author JrYYY
 */
@Component
public class ImageUtils {

    /**
     * 排序计算主题色
     * @param map   RGB
     * @return  主题色
     */
    private static String getMostCommonColour(Map map) {
        List list = new LinkedList(map.entrySet());
        list.sort((o1,o2) -> ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue()));
        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRgbArr((Integer) me.getKey());
        return "#" + Integer.toHexString(rgb[0]) + "" + Integer.toHexString(rgb[1]) + "" + Integer.toHexString(rgb[2]);
    }


    /**
     * 获取RGB
     * @param pixel 像素点
     * @return RGB
     */
    private static int[] getRgbArr(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};

    }

    /**
     * 转变为灰色
     * @param rgbArr  RGB
     * @return  灰度
     */
    private static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            return rbDiff <= tolerance && rbDiff >= -tolerance;
        }
        return true;
    }

    /**
     * 主题颜色
     * @param image   图片
     * @return  主题颜色
     */
    public String dominantColor(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRgbArr(rgb);
                if (!isGray(rgbArr)) {
                    Integer counter = m.get(rgb);
                    if (counter == null){
                        counter = 0;
                    }
                    counter++;
                    m.put(rgb, counter);
                }
            }
        }
        return getMostCommonColour(m);
    }
}
