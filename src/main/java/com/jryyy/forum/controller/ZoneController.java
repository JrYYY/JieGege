package com.jryyy.forum.controller;

import com.jryyy.forum.exception.PreconditionFailedException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.tool.SaveFileUtils;
import com.jryyy.forum.tool.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zone")
public class ZoneController {

    @Value("${cds.zoneImages}")
    String url;

    String http = "http://192.163.19.59:9896/file:";

    @Autowired
    SaveFileUtils file;

    @PostMapping
    @UserLoginToken
    public Response writeUserZone(String msg, MultipartFile[] files, HttpSession session) throws Exception {
        if (msg == null && files.length == 0)
            throw new PreconditionFailedException("请填写上传内容");
        System.out.println(msg);
        List<String> fileUrls = file.saveTalkImg(url, files);
        List<String> urls = new ArrayList<>();
        for (String fileUrl : fileUrls) {
            urls.add(http + fileUrl);
        }
        return new Response<List<String>>(urls);
    }
}
