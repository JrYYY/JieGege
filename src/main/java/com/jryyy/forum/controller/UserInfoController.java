package com.jryyy.forum.controller;

import com.jryyy.forum.annotation.UserLoginToken;
import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.UserInfoRequest;
import com.jryyy.forum.tool.file.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;

/**
 * 用户信息控制层
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    SaveFileUtils saveFile;

    @Value("${cds.imagesPath}")
    private String imagesPath;

    @PutMapping
    @UserLoginToken
    public Response addUserInformation(@Valid @ModelAttribute UserInfoRequest userInfo,
                                       @RequestParam("avatar") MultipartFile avatar,
                                       HttpSession session) throws Exception {
        Integer userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        InetAddress addr = InetAddress.getLocalHost();
        String servlet_url = "http://" + addr.getHostAddress() + ":9896/file:";
        System.out.println(servlet_url + imagesPath + saveFile.savePicture(imagesPath, userId, avatar));
        System.out.println(userInfo.toString());
        return new Response<String>(servlet_url + imagesPath + saveFile.savePicture(imagesPath, 1000, avatar));
    }
}
