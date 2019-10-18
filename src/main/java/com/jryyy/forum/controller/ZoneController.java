package com.jryyy.forum.controller;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.UserZoneRequest;
import com.jryyy.forum.tool.annotation.UserLoginToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ZoneController {

    @PostMapping
    @UserLoginToken
    public Response writeUserZone(UserZoneRequest request, HttpSession session) throws Exception {
        return null;
    }
}
