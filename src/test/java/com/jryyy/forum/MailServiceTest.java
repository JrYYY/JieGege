package com.jryyy.forum;

import com.jryyy.forum.utils.CodeMailUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailServiceTest {

    @Autowired
    private CodeMailUtils mailUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSimpleMail() throws Exception {
        mailUtils.sendSimpleMail("1952381587@qq.com", "test simple mail");
        System.out.println(redisTemplate.opsForValue().get("1952381587@qq.com"));
    }

    @Test
    public void CsetListRedis() {
        Set<Object> locked = new HashSet<>();

        redisTemplate.opsForValue();
    }

}