package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.tool.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @UserLoginToken
    @GetMapping("/string")
    public String testString(HttpSession session) {
        System.out.println(session.getAttribute(Constants.USER_ID_STRING));
        return "运行成功！";
    }

    @DeleteMapping("/check")
    public void testCheck() throws Exception {
        userInfoMapper.deleteCheckInDate(1010);
    }
}
