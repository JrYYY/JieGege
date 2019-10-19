package com.jryyy.forum.services;

import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.UserZone;

import java.util.List;

public interface ZoneService {

    /**
     * @param
     * @return
     * @throws Exception
     */
    Response pagingQueryZone(int method) throws Exception;

    Response findZoneByUserId(int userId) throws Exception;

    Response findZoneById(int id) throws Exception;

    Response deleteZoneById(int userId, int id) throws Exception;

    Response writeZone(UserZone userZone, List<String> imgUrl) throws Exception;
}
