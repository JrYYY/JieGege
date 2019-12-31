package com.jryyy.forum;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;


public class ImageTester {


    public static void main(String[] args) throws Exception {
        File file = new File("D:\\home\\zone\\image\\8\\zoneImg_1571577081781.jpg");


        BufferedImage image = ImageIO.read(new FileInputStream(file));
        int height = image.getHeight();
        int width = image.getWidth();

        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                // Filter out grays....                
                if (!isGray(rgbArr)) {
                    Integer counter = (Integer) m.get(rgb);
                    if (counter == null)
                        counter = 0;
                    counter++;
                    m.put(rgb, counter);
                }
            }
        }
        String colourHex = getMostCommonColour(m);
        System.out.println(colourHex);
    }


    public static String getMostCommonColour(Map map) {
        List list = new LinkedList(map.entrySet());
        list.sort((o1,o2)->(
                ((Comparable)((Map.Entry)o1).getValue())
                        .compareTo(((Map.Entry)o2).getValue())
        ));

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArr((Integer) me.getKey());
        return "#" + Integer.toHexString(rgb[0]) + "" + Integer.toHexString(rgb[1]) + "" + Integer.toHexString(rgb[2]);
    }

    /**
     * 获取RGB
     * @param pixel 像素点
     * @return RGB
     */
    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};

    }

    private static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance)
            return rbDiff <= tolerance && rbDiff >= -tolerance;
        return true;
    }
}