package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;

public interface ZoneCommentService {

    Response findZoneComments(int zoneId, int currIndex, int pageSize) throws Exception;

    Response saveZoneComment(int userId, int zoneId, String msg) throws Exception;

    Response removeZoneComment(int zoneId) throws Exception;

}
