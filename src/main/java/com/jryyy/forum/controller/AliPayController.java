package com.jryyy.forum.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author OU
 */
@RestController
@RequestMapping("/pay")
public class AliPayController {


    @PostMapping("/notify")
    public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {

    }


}
