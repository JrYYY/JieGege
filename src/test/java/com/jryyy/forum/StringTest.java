package com.jryyy.forum;

import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.services.imp.UserInfoServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTest {

    @Autowired
    UserZoneMapper userZoneMapper;

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
        UserInfoServiceImp service = new UserInfoServiceImp();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Date date1 = new Date(2019, 10, 10);
        Date date2 = new Date(2019, 10, 19);
        // System.out.println(service.differentDays(date1,date2));
    }


}
