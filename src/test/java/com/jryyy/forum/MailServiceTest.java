package com.jryyy.forum;

import com.jryyy.forum.tool.email.CodeMailUtils;
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
        mailUtils.sendSimpleMail("ityouknow@126.com", "test simple mail");
    }

    @Test
    public void CsetListRedis() {
        Set<Object> locked = new HashSet<>();

        redisTemplate.opsForValue();
    }

    @Test
    public void setRedis() {
        redisTemplate.opsForValue().set("id", 1011);
    }

    @Test
    public void getRedis() {
        System.out.println(redisTemplate.opsForList().range("userlist", 0, 10));
    }

    @Test
    public void deleteRedis() {
        redisTemplate.delete("userlist");
    }

}