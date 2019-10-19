package com.jryyy.forum.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class CodeMailUtils {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = createCode();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("请填入您的验证码: " + code);
        try {
            mailSender.send(message);
            System.out.println("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MailSendException("邮件发送失败");
        }

    }

    /**
     * 产生验证码
     *
     * @return 验证码
     */
    public String createCode() {
        String codeNum = "";
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                "Z"};
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int next = random.nextInt(62);
            // System.out.println(next);
            codeNum += numbers[next % 62];
        }
        return codeNum;
    }
}