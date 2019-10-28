package com.jryyy.forum.utils;

import com.jryyy.forum.constant.status.GlobalStatus;
import com.jryyy.forum.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Component
public class CodeMailUtil {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String subject) throws GlobalException {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = createCode();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("请填入您的验证码:  " + code);
        try {
            mailSender.send(message);
            redisTemplate.opsForValue().set(to, code, 300L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.failedToApplyForVerificationCode);
        }
    }

    /**
     * 产生验证码
     *
     * @return 验证码
     */
    public String createCode() {
        StringBuilder codeNum = new StringBuilder();
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
            codeNum.append(numbers[next % 62]);
        }
        return codeNum.toString();
    }
}