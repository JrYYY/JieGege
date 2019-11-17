package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.dao.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
//@UserLoginToken
@RequestMapping("/test")
public class testController {

    @Autowired
    UserInfoMapper userInfoMapper;


    @GetMapping("/string")
    // @CrossOrigin(origins = "http://localhost:5500")
    public String testString(HttpSession session) {
        System.out.println(session.getAttribute(Constants.USER_ID_STRING));
        return "运行成功+7987";
    }

    @DeleteMapping("/check")
    //@PassToken
    public String testCheck() throws Exception {
        // userInfoMapper.deleteCheckInDate(1010);
        return "测试成功";
    }

    public void greeting(String message) throws Exception {
        Thread.sleep(1000); // 模拟处理延时
        String msg = "Hello, " + HtmlUtils.htmlEscape(message) + "!";
        //根据传入的信息，返回一个欢迎消息.
    }
}
