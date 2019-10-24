package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.ZoneCommentMapper;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.services.ZoneCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneCommentServiceImp implements ZoneCommentService {

    @Autowired
    ZoneCommentMapper zoneCommentMapper;

    @Override
    public Response findZoneComments(int zoneId, int curPage, int pageSize) throws Exception {
        return new Response<>(zoneCommentMapper.
                findCommentByZoneId(zoneId, (curPage - 1) * pageSize, pageSize));
    }

    @Override
    public Response saveZoneComment(int userId, int zoneId, String msg) throws Exception {
        zoneCommentMapper.insertComment(userId, zoneId, msg);
        return new Response();
    }

    @Override
    public Response removeZoneComment(int zoneId) throws Exception {
        zoneCommentMapper.removeComment(zoneId);
        return new Response();
    }
}
