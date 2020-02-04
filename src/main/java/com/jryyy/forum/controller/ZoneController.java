package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.GetZoneRequest;
import com.jryyy.forum.model.request.ZoneRequest;
import com.jryyy.forum.service.ZoneService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.ResultSet;

/**
 * @author OU
 */
@RestController
@RequestMapping("/zone")
public class ZoneController {

    private  final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    /**
     * 写入空间
     * @param request   {@link ZoneRequest}
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    @UserLoginToken
    public Response insertZone(@PathVariable Integer userId,@ModelAttribute @Valid ZoneRequest request)throws Exception{
        request.setUserId(userId);
        return zoneService.insertZone(request);
    }


    /**
     * 读取用户空间
     * @param userId    用户id
     * @param request   {@link GetZoneRequest}
     * @return {@link com.jryyy.forum.model.response.ZoneResponse}
     * @throws Exception
     */
    @UserLoginToken
    @GetMapping("/{userId}")
    public Response getUserZone(@PathVariable Integer userId,@ModelAttribute @Valid GetZoneRequest request)throws Exception{
        return zoneService.getZone(userId,request);
    }

    /**
     * 删除空间
     * @param userId    用户id
     * @param zoneId    空间id
     * @return  {@link Response}
     * @throws Exception
     */
    @DeleteMapping("/{userId}")
    public Response deleteZone(@PathVariable Integer userId,@RequestParam Integer zoneId)throws Exception{
        return zoneService.deleteZone(userId,zoneId);
    }

    /**
     * 空间评论
     * @param zoneId 空间id
     * @return  {@link Response }
     * @throws Exception
     */
    @GetMapping("/comment")
    public Response findZoneComment(@RequestParam Integer zoneId, @RequestParam Integer curPage,
                                    @RequestParam Integer pageSize)throws Exception{
        return zoneService.findZoneComment(zoneId,curPage,pageSize);
    }

    @GetMapping("/reply")
    public Response findZoneComment(@RequestParam Integer id)throws Exception{
        return zoneService.findReply(id);
    }

    /**
     *
     * @param userId    用户id
     * @param id        空间id
     * @param comment       消息
     * @return  {@link Response}
     * @throws Exception
     */
    @UserLoginToken
    @PostMapping("/comment/{userId}")
    public Response comment(@PathVariable Integer userId, @RequestParam Integer id,@RequestParam String comment,Integer pId)throws Exception{
        return zoneService.comment(userId,id,comment,pId);
    }


    /**
     * 点赞
     * @param userId    用户id
     * @param zoneId    空间id
     * @return  {@link Response}
     * @throws Exception
     */
    @UserLoginToken
    @PutMapping("/like/{userId}")
    public Response like(@PathVariable Integer userId,@RequestParam Integer zoneId)throws Exception{
        return zoneService.like(userId, zoneId);
    }
}
