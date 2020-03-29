package com.jryyy.forum;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulary {


    @Test
    public void test() {
        StringBuilder sql = new StringBuilder("insert into thesaurus(English,Chinese)values");
        try {
            File file = new File("E:\\LearningProject\\Forum\\大学英语词汇.txt");
            String regex2 = "^[a-zA-Z]+ ";
            String regex3 = " .+$";
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);

            String line = br.readLine();
            int n = 1;
            while (line != null && !"".equals(line)) {

                String e = getWord(line, regex2);
                String c = getWord(line, regex3);
                // 一次读入一行数据
                line = br.readLine();
                if ((line != null && line.equals(""))) {
                    sql.append(String.format("('%s','%s');", e, c));
                } else {
                    sql.append(String.format("('%s','%s'),", e, c));
                }
                n++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
    }

    private String getWord(String s, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(s);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return null;
    }

}
