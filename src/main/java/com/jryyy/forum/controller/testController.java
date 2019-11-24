package com.jryyy.forum.controller;


import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.models.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
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

    @PostMapping(value = "/json")
    public String testJson(@RequestBody @Valid UserRequest request) throws Exception {
        log.info(request.toString());
        return request.toString();
    }
}
