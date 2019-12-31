package com.jryyy.forum.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author JrYYY
 */
@Component
public class CaptchaUtils {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private final int verification_code_length = 6;

    /**
     * 生成字母数字验证码
     * @param key 身份验证
     * @return 验证码
     */
    public String generateAlphanumericVerificationCode(String key) {
        StringBuilder codeNum = new StringBuilder();
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                "Z"};
        Random random = new Random();
        for (int i = 0; i < verification_code_length; i++) {
            int next = random.nextInt(62);
            codeNum.append(numbers[next % 62]);
        }
        redisTemplate.opsForValue().set(key, codeNum.toString(), 300L, TimeUnit.SECONDS);
        return codeNum.toString();
    }

    /**
     * 生成数字验证码
     * @param key   身份验证
     * @return  验证码
     */
    public String generateDigitalVerificationCode(String key){
        StringBuilder codeNum = new StringBuilder();
        Random random = new Random();
        for(int i = 0;i < verification_code_length;i++){
            codeNum.append(random.nextInt(10));
        }
        redisTemplate.opsForValue().set(key, codeNum.toString(), 300L, TimeUnit.SECONDS);
        return codeNum.toString();
    }


}