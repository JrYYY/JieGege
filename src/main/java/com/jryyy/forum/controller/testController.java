package com.jryyy.forum.controller;

import com.jryyy.forum.annotation.UserLoginToken;
import com.jryyy.forum.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class testController {

    @UserLoginToken
    @GetMapping("/string")
    public String testString(HttpSession session) {
        System.out.println(session.getAttribute(Constants.USER_ID_STRING));
        return "运行成功！";
    }
}
