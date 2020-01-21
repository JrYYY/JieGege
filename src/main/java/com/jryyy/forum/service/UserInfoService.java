package com.jryyy.forum.service;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.UserInfoRequest;

public interface UserInfoService {

    Response viewUserInfo(Integer userId)throws Exception;

    Response changeUserInfo(UserInfoRequest request)throws Exception;

    Response searchUser(String info)throws Exception;
}
