package com.jryyy.forum;

import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.model.ZoneImg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {

    @Autowired
    UserZoneMapper userZoneMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void testRedis() throws Exception {

    }

    @Test
    public void test() throws Exception {
        List<ZoneImg> zoneImgs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            zoneImgs.add(ZoneImg.builder().zoneId(1011).imgUrl("测试").build());
        }
        for (ZoneImg z : zoneImgs
        ) {
            System.out.println(z.toString());
        }
        //userZoneMapper.insertZoneImg(zoneImgs);
    }




}
