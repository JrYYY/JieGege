package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.dao.ZonePraiseMapper;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.UserZone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.models.request.ZoneRequest;
import com.jryyy.forum.models.response.ZoneListResponse;
import com.jryyy.forum.models.response.ZoneResponse;
import com.jryyy.forum.services.ZoneService;
import com.jryyy.forum.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service("ZoneService")
public class ZoneServiceImp implements ZoneService {

    @Autowired
    UserZoneMapper userZoneMapper;
    @Autowired
    ZonePraiseMapper zonePraiseMapper;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.url}")
    private String file_url;
    @Autowired
    private FileUtil fileUtil;

    @Override
    public Response writeZone(ZoneRequest request) throws Exception {
        UserZone userZone = request.toUserZone();
        userZoneMapper.insertZone(userZone);
        if (request.getFiles() != null) {
            for (MultipartFile img : request.getFiles()) {
                String fileName = fileUtil.saveTalkImg(uploadFolder + userZone.getId() + "/", img);
                BufferedImage image = ImageIO.read(new FileInputStream(
                        uploadFolder + userZone.getId() + "/" + fileName));
                userZoneMapper.insertZoneImg(ZoneImg.builder()
                        .imgUrl(userZone.getId() + "/" + fileName)
                        .zoneId(userZone.getId())
                        .width(image.getWidth())
                        .height(image.getHeight())
                        .build());
            }
        }
        return new Response();
    }

    @Override
    public Response findAllZone(int curPage, int pageSize, int mode) throws Exception {
        int totalNum = userZoneMapper.countZoneNum();
        int totalPageNum = (int) Math.ceil((float) totalNum / pageSize);
        ZoneListResponse response = ZoneListResponse.builder()
                .curPageNumber(curPage).numberPerPage(pageSize)
                .totalNum(totalNum).totalPageNum(totalPageNum)
                .hasNext(curPage != totalPageNum).hasPrev(curPage != 1)
                .build();
        List<ZoneResponse> zones = userZoneMapper.findAllZone(
                (curPage - 1) * pageSize, pageSize, mode);
        for (ZoneResponse zone : zones) {
            zone.setPraise(zonePraiseMapper
                    .countZonePraise(zone.getId()));
            zone.toZoneImgList(userZoneMapper, file_url);
        }
        response.setZones(zones);
        return new Response<>(response);
    }

    @Override
    public Response findUserZone(int curPage, int pageSize, int userId, int mode) throws Exception {
        int totalNum = userZoneMapper.countZoneNumByUserId(userId);
        int totalPageNum = (int) Math.ceil((float) totalNum / pageSize);
        ZoneListResponse response = ZoneListResponse.builder()
                .curPageNumber(curPage).numberPerPage(pageSize)
                .totalNum(totalNum).totalPageNum(totalPageNum)
                .hasNext(curPage != totalPageNum).hasPrev(curPage != 1)
                .build();
        List<ZoneResponse> zones = userZoneMapper.findZoneByUser(
                (curPage - 1) * pageSize, pageSize, userId, mode);
        for (ZoneResponse zone : zones) {
            zone.setPraise(zonePraiseMapper
                    .countZonePraise(zone.getId()));
            zone.toZoneImgList(userZoneMapper, file_url);
        }
        response.setZones(zones);
        return new Response<>(response);
    }


    @Override
    public Response findZoneById(int id) throws Exception {
        //ZoneResponse zone = userZoneMapper.
        return null;
    }

    @Override
    public Response deleteZoneById(int userId, int id) throws Exception {
        int ID = userZoneMapper.findUserIdById(id);
        if (ID == userId)
            throw new AccessDeniedException("非自己上传，无法删除");
        userZoneMapper.deleteZone(id);
        userZoneMapper.deleteAllZoneImg(id);
        fileUtil.deleteFile(uploadFolder + "\\" + id);
        return new Response();
    }
}
