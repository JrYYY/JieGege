package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.ZoneCommentMapper;
import com.jryyy.forum.dao.ZoneMapper;
import com.jryyy.forum.dao.ZonePraiseMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.Zone;
import com.jryyy.forum.model.ZoneImg;
import com.jryyy.forum.model.request.GetZoneRequest;
import com.jryyy.forum.model.request.ZoneRequest;
import com.jryyy.forum.model.response.PaginationResponse;
import com.jryyy.forum.model.response.UserInfoResponse;
import com.jryyy.forum.model.response.ZoneCommentResponse;
import com.jryyy.forum.model.response.ZoneResponse;
import com.jryyy.forum.service.ZoneService;
import com.jryyy.forum.utils.FileUtils;
import com.jryyy.forum.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author OU
 */
@Slf4j
@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneMapper zoneMapper;

    private final ZonePraiseMapper zonePraiseMapper;

    private final ZoneCommentMapper zoneCommentMapper;

    private final UserInfoMapper userInfoMapper;

    private final FileUtils fileUtils;

    private final ImageUtils imageUtils;

    @Value("${file.url}")
    private String fileUrl;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public ZoneServiceImpl(ZoneMapper zoneMapper, ZonePraiseMapper zonePraiseMapper,
                           ZoneCommentMapper zoneCommentMapper, UserInfoMapper userInfoMapper,
                           FileUtils fileUtils, ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
        this.zoneCommentMapper = zoneCommentMapper;
        this.zoneMapper = zoneMapper;
        this.userInfoMapper = userInfoMapper;
        this.zonePraiseMapper = zonePraiseMapper;
        this.fileUtils = fileUtils;
    }

    @Override
    public Response insertZone(ZoneRequest request) throws Exception {
        log.info(request.toString());
        Zone zone = request.getZone();
        zoneMapper.insertZone(zone);
        if (request.getFiles() != null) {
            List<ZoneImg> zoneImgList = request.saveImage(zone.getId(), uploadFolder, fileUtils, imageUtils);
            zoneImgList.forEach(zoneMapper::insertZoneImg);
        }
        return new Response();
    }

    @Override
    public Response viewSpace(Integer userId, GetZoneRequest request) throws Exception {
        log.info(request.toString());
        List<ZoneResponse> responses = new ArrayList<>();
        int curPage = request.getCurPage();
        request.setCurPage((request.getCurPage()-1)*request.getPageSize());
        List<Zone> zones = zoneMapper.findZone(request);
        zones.forEach(zone -> {
            UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(zone.getUserId());
            List<Integer> praise = zonePraiseMapper.selectZonePraise(zone.getId());
            List<UserInfoResponse> favoriteUser = new ArrayList<>();
            praise.forEach(o -> favoriteUser.add(UserInfoResponse.userInfoResponse(userInfoMapper, o, fileUrl)));
            userInfo.addAvatarUrl(fileUrl);
            ZoneResponse response = ZoneResponse.builder().comment(zoneCommentMapper.count(zone.getId()))
                    .userInfo(userInfo).favoriteUser(favoriteUser).build();
            response.setZone(zone);
            response.setIsFavorite(zonePraiseMapper.isFavorite(userId,zone.getId()) != null);
            response.addZoneImage(zoneMapper, fileUrl);
            responses.add(response);
        });
        int count = request.getId() == null ? zoneMapper.countZoneNum() :
                zoneMapper.countZoneNumByUserId(request.getId());
        return new Response<>(new PaginationResponse<>(responses,count,
                curPage, request.getPageSize()));
    }

    @Override
    public Response viewUpdateSpace(Integer id) throws Exception {
        return null;
    }

    @Override
    public Response deleteZone(Integer userId, Integer zoneId) throws Exception {
        if(zoneMapper.deleteZone(userId,zoneId)  < 1) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess); }
        zoneMapper.deleteAllZoneImg(zoneId);
        zonePraiseMapper.deleteAllZonePraise(zoneId);
        zoneCommentMapper.removeAllComment(zoneId);
        return new Response();
    }

    @Override
    public Response findZoneComment(Integer zoneId,Integer currIndex,Integer pageSize) throws Exception {
       try {
           List<ZoneCommentResponse> responses = zoneCommentMapper.findCommentByZoneId(zoneId,(currIndex-1)*pageSize,pageSize);
           log.info(responses.toString());
           responses.forEach(comment->{
                UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(comment.getUserId());
                userInfo.addAvatarUrl(fileUrl);
                comment.setUserInfo(userInfo);
           });
           return new Response<>(new PaginationResponse<>(responses,zoneCommentMapper.count(zoneId),currIndex,pageSize));
       }catch (Exception e){
           e.printStackTrace();
           throw e;
       }
    }

    @Override
    public Response findReply(Integer id) throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<ZoneCommentResponse> responses = zoneCommentMapper.findReply(id);
        log.info(responses.toString());
        responses.forEach(comment->{
            UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(comment.getUserId());
            userInfo.addAvatarUrl(fileUrl);
            comment.setUserInfo(userInfo);
        });
        data.put("reply",responses);
        data.put("totalNum",zoneCommentMapper.countReply(id));
        return new Response<>(data);
    }

    @Override
    public Response comment(Integer userId, Integer id, String comment,Integer pId) throws Exception {
        if(zoneMapper.findZoneById(id) == null){
            throw new GlobalException(GlobalStatus.unauthorizedAccess); }
        if(pId != null && zoneCommentMapper.findCommentById(pId) == null){
            throw new GlobalException(GlobalStatus.unauthorizedAccess); }
        zoneCommentMapper.insertComment(id,userId,comment,pId);
        return new Response();
    }


    @Override
    public Response like(Integer userId, Integer zoneId) throws Exception {
        if(zonePraiseMapper.deleteZonePraise(userId,zoneId) < 1){
           zonePraiseMapper.insertZonePraise(userId,zoneId);
           zoneMapper.updatePraise(zonePraiseMapper.countZonePraise(zoneId),zoneId);
           return new Response<>("点赞成功");
        }
        zoneMapper.updatePraise(zonePraiseMapper.countZonePraise(zoneId),zoneId);
        return new Response<>("取消成功");
    }
}
