package com.jryyy.forum.controller;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.ZoneCommentService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/zone")
public class ZoneCommentController {

    @Autowired
    ZoneCommentService zoneCommentService;

    @GetMapping("/comment/{id}/{currIndex}/{pageSize}")
    public Response findComment(@PathVariable int id,
                                @PathVariable int currIndex,
                                @PathVariable int pageSize) throws Exception {
        return zoneCommentService.findZoneComments(id, currIndex, pageSize);
    }

    @UserLoginToken
    @PostMapping("/comment")
    public Response saveComment(@RequestParam int id, @RequestParam String msg,
                                HttpSession session) throws Exception {
        int userId = (int) session.getAttribute(Constants.USER_ID_STRING);
        return zoneCommentService.saveZoneComment(userId, id, msg);
    }
}
