package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.GetZoneCommentRequest;

public interface ZoneCommentService {

    Response findZoneComments(GetZoneCommentRequest request) throws Exception;

    Response saveZoneComment(int userId, int zoneId, String msg) throws Exception;

    Response removeZoneComment(int zoneId) throws Exception;

}
