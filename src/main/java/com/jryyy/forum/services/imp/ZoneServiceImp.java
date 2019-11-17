package com.jryyy.forum.services.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.dao.ZonePraiseMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.UserZone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.models.request.GetZoneRequest;
import com.jryyy.forum.models.request.ZoneRequest;
import com.jryyy.forum.models.response.ZoneDetailResponse;
import com.jryyy.forum.models.response.ZoneListResponse;
import com.jryyy.forum.models.response.ZonePraiseResponse;
import com.jryyy.forum.models.response.ZoneResponse;
import com.jryyy.forum.services.ZoneService;
import com.jryyy.forum.utils.DominantColorUtils;
import com.jryyy.forum.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.List;

@Service("ZoneService")
public class ZoneServiceImp implements ZoneService {

    @Autowired
    UserZoneMapper userZoneMapper;

    @Autowired
    ZonePraiseMapper zonePraiseMapper;

    @Autowired
    private FileUtils fileUtil;

    @Autowired
    private DominantColorUtils dominantColorUtils;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.url}")
    private String file_url;

    @Override
    public Response writeZone(ZoneRequest request) throws Exception {
        UserZone userZone = request.toUserZone();
        userZoneMapper.insertZone(userZone);
        if (request.getFiles() != null) {

            for (MultipartFile img : request.getFiles()) {
                String fileName = fileUtil.saveTalkImg(uploadFolder + "zone/image/" + userZone.getId() + "/", img);

                String url = uploadFolder + "zone/image/" + userZone.getId() + "/" + fileName;
                BufferedImage image = ImageIO.read(new FileInputStream(url));
                userZoneMapper.insertZoneImg(ZoneImg.builder()
                        .imgUrl("zone/image/" + userZone.getId() + "/" + fileName)
                        .zoneId(userZone.getId())
                        .width(image.getWidth())
                        .height(image.getHeight())
                        .dominantColor(dominantColorUtils.dominantColor(url))
                        .build());
            }
        }
        return new Response();
    }

    @Override
    public Response findAllZone(GetZoneRequest request) throws Exception {
        int totalNum = userZoneMapper.countZoneNum();
        int totalPageNum = (int) Math.ceil((float) totalNum / request.getPageSize());
        if (!(request.getCurPage() > 0 && request.getCurPage() <= totalPageNum))
            throw new GlobalException(GlobalStatus.noSuchPage);

        List<ZoneResponse> zones = userZoneMapper.findAllZone(
                (request.getCurPage() - 1) * request.getPageSize(),
                request.getPageSize(), request.getMode());

        ZoneListResponse response = ZoneListResponse.builder()
                .curPageNumber(request.getCurPage()).numberPerPage(request.getPageSize())
                .totalNum(totalNum).totalPageNum(totalPageNum)
                .hasNext(request.getCurPage() != totalPageNum)
                .hasPrev(request.getCurPage() != 1)
                .build();

        for (ZoneResponse zone : zones) {
            zone.setPraise(zonePraiseMapper
                    .countZonePraise(zone.getId()));
            zone.toZoneImgList(userZoneMapper, file_url);
        }
        response.setZones(zones);
        return new Response<>(response);
    }

    @Override
    public Response findUserZone(GetZoneRequest request, int userId) throws Exception {
        try {
            int totalNum = userZoneMapper.countZoneNumByUserId(userId);
            int totalPageNum = (int) Math.ceil((float) totalNum / request.getPageSize());

            if (!(request.getCurPage() > 0 && request.getCurPage() <= totalPageNum))
                throw new GlobalException(GlobalStatus.noSuchPage);

            ZoneListResponse response = ZoneListResponse.builder()
                    .curPageNumber(request.getCurPage())
                    .numberPerPage(request.getPageSize())
                    .totalNum(totalNum).totalPageNum(totalPageNum)
                    .hasNext(request.getCurPage() != totalPageNum)
                    .hasPrev(request.getCurPage() != 1)
                    .build();

            List<ZoneResponse> zones = userZoneMapper.findZoneByUser(
                    (request.getCurPage() - 1) * request.getPageSize(),
                    request.getPageSize(), userId, request.getMode());

            for (ZoneResponse zone : zones) {
                zone.setPraise(zonePraiseMapper
                        .countZonePraise(zone.getId()));
                zone.toZoneImgList(userZoneMapper, file_url);
            }
            response.setZones(zones);
            return new Response<>(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Response findZoneById(int id) throws Exception {
        ZoneResponse zone = userZoneMapper.findZoneById(id);
        if (zone == null)
            throw new GlobalException(GlobalStatus.noResourcesFound);
        List<ZonePraiseResponse> praise = zonePraiseMapper.selectZonePraise(zone.getId());
        zone.toZoneImgList(userZoneMapper, file_url);
        ZoneDetailResponse response = new ZoneDetailResponse(zone);
        response.setUsers(praise);
        return new Response<>(response);
    }

    @Override
    public Response deleteZoneById(int userId, int id) throws Exception {
        int ID = userZoneMapper.findUserIdById(id);
        if (ID == userId)
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        userZoneMapper.deleteZone(id);
        userZoneMapper.deleteAllZoneImg(id);
        zonePraiseMapper.deleteAllZonePraise(id);
        fileUtil.deleteFile(uploadFolder + "\\" + id);
        return new Response();
    }

    @Override
    public Response likeOrCancel(int userId, int zoneId) throws Exception {
        Integer judge = zonePraiseMapper.findPraiseByUser(userId, zoneId);
        if (judge == null)
            zonePraiseMapper.insertZonePraise(userId, zoneId);
        else
            zonePraiseMapper.deleteZonePraise(userId, zoneId);
        int count = zonePraiseMapper.countZonePraise(zoneId);
        userZoneMapper.updatePraise(count, zoneId);
        return new Response<>(true);
    }

    @Override
    public Response liked(int userId, int zoneId) throws Exception {
        Integer judge = zonePraiseMapper.findPraiseByUser(userId, zoneId);
        return new Response<>(judge != null);
    }

}
