package com.jryyy.forum;

import com.jryyy.forum.dao.ZoneMapper;
import com.jryyy.forum.service.UserInfoService;
import com.jryyy.forum.service.WordService;
import com.jryyy.forum.utils.sql.model.BaseModel;
import com.jryyy.forum.utils.sql.test.TestMapper;
import com.jryyy.forum.utils.sql.test.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {

    @Autowired
    ZoneMapper userZoneMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    WordService wordMemoryService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    TestMapper testMapper;

    @Test
    public void test1() throws Exception {
        User user = User.builder().username("DOU1010").build();
        testMapper.findById(new BaseModel<>(user)).forEach(System.out::println);
    }

//    @Test
//    public void testEmoji()throws Exception{
//       userInfoMapper.findAllUserId().forEach(
//               o->userInfoMapper.updateUsername(o,KayOrUrl.username(o))
//       );
//    }

//
//    @Test
//    public void testRedis() throws Exception {
//        wordMemoryService.getWordLibrary(1010);
//    }
//
//    @Test
//    public void test()throws Exception{
//        System.out.println(wordMemoryService.memory(1010,true));
//    }


//
//    @Test
//    public void redis()throws Exception{
//        System.out.println(
//                Objects.requireNonNull(redisTemplate.opsForList()
//                        .size(KayOrUrl.unrecordedWordsKey(1040)))
//                .getClass()
//        );
//    }
//
//    @Test
//    public void test() throws Exception {
//        List<ZoneImg> zoneImgs = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            zoneImgs.add(ZoneImg.builder().zoneId(1011).imgUrl("测试").build());
//        }
//        for (ZoneImg z : zoneImgs
//        ) {
//            System.out.println(z.toString());
//        }
//        //userZoneMapper.insertZoneImg(zoneImgs);
//    }
//
//    @Test
//    public void test1() {
//        String str = "{\"test1\":{\"name\":\"zhangsan\"},\"test2\":{\"name\":\"lisi\"},\"test3\":{\"name\":\"wanger\"}}";
//
//        HashMap<String, NameEntity> nameMap =
//                JSON.parseObject(str, new TypeReference<HashMap<String, NameEntity>>() {
//                });
//
//        HashMap<String, NameEntity> map = getHashList(str, new TypeReference<HashMap<String, NameEntity>>() {
//        });
//
//        System.out.println(nameMap.toString());
//
//        //其中Map中key为String类型，value为 NameEntity类型的实体
//    }
//
//    public <T> T getHashList(String str, TypeReference<T> obj) {
//        if (str != null) { return JSON.parseObject(str, obj.getType()); }
//        return null;
//    }

}
