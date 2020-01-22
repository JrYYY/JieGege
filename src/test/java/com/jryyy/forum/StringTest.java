package com.jryyy.forum;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jryyy.forum.constant.KayAndUrl;
import com.jryyy.forum.dao.ZoneMapper;
import com.jryyy.forum.model.ZoneImg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {

    @Autowired
    ZoneMapper userZoneMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedis() throws Exception {
        System.out.println(redisTemplate.opsForHash().get(KayAndUrl.ONLINE_USER_LIST_KEY, KayAndUrl.userKey(1040)));
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

    @Test
    public void test1() {
        String str = "{\"test1\":{\"name\":\"zhangsan\"},\"test2\":{\"name\":\"lisi\"},\"test3\":{\"name\":\"wanger\"}}";

        HashMap<String, NameEntity> nameMap =
                JSON.parseObject(str, new TypeReference<HashMap<String, NameEntity>>() {
                });

        HashMap<String, NameEntity> map = getHashList(str, new TypeReference<HashMap<String, NameEntity>>() {
        });

        System.out.println(nameMap.toString());

        //其中Map中key为String类型，value为 NameEntity类型的实体
    }

    public <T> T getHashList(String str, TypeReference<T> obj) {

        if (str != null) {
            return JSON.parseObject(str, obj.getType());
        }
        return null;
    }




}
