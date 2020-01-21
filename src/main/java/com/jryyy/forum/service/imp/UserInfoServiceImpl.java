package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author OU
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Response viewUserInfo(Integer userId) throws Exception {
        return new Response<>(userInfoMapper.selectUserInfo(userId));
    }

    @Override
    public Response changeUserInfo(UserInfoRequest request) throws Exception {
        try {
            userInfoMapper.updateUserInfo(request);
            return new Response();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response searchUser(String info) throws Exception {
        return new Response<>(userInfoMapper.findInfoByCondition(info));
    }
}
