package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.UserFriendMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.exception.PreconditionFailedException;
import com.jryyy.forum.models.CheckIn;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.UserInfo;
import com.jryyy.forum.models.request.UserInfoRequest;
import com.jryyy.forum.models.response.UserInfoResponse;
import com.jryyy.forum.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("UserInfoService")
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserFriendMapper userFriendMapper;

    @Override
    public Response viewUserPersonalInformation(int userId) throws Exception {
        try {
            UserInfo userInfo = userInfoMapper.selectUserInfo(userId);
            Integer following = userFriendMapper.followingTotalByFId(userId);
            Integer followers = userFriendMapper.followersNumByUId(userId);
            return new Response<UserInfoResponse>(UserInfoResponse.builder()
                    .username(userInfo.getUsername()).avatar(userInfo.getAvatar())
                    .age(userInfo.getAge()).sex(userInfo.getSex())
                    .followersNum(followers).followingNum(following)
                    .checkInDate(userInfo.getCheckInDate())
                    .checkInDays(userInfo.getCheckInDays())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查看用户个人基本信息失败");
        }
    }

    @Override
    public Response changeUserPersonalBasicInformation(UserInfoRequest request) throws Exception {
        try {
            userInfoMapper.updateUserInfo(request);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("修改用户信息失败");
        }
    }

    @Override
    public Response checkIn(int userId) throws Exception {
        CheckIn check = userInfoMapper.selectCheckIn(userId);
        Response response = whetherToCheckIn(userId);
        if ((boolean) response.getData())
            throw new PreconditionFailedException("已签到，签到失败！");
        try {
            System.out.println(check.getCheckInDays());
            userInfoMapper.checkIn(userId, check.getCheckInDays() + 1);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("服务器错误");
        }
    }

    @Override
    public Response whetherToCheckIn(int userId) throws Exception {
        CheckIn check = userInfoMapper.selectCheckIn(userId);
        Date date = new Date();

        if (check.getCheckInDate() == null)
            return new Response<Boolean>(false);


        if (check.getCheckInDate().getYear() < date.getYear())
            return new Response<Boolean>(false);


        if (check.getCheckInDate().getYear() == date.getYear())
            if (check.getCheckInDate().getMonth() < date.getMonth())
                return new Response<Boolean>(false);

        if (check.getCheckInDate().getYear() == date.getYear())
            if (check.getCheckInDate().getMonth() == date.getMonth())
                if (check.getCheckInDate().getDay() < date.getDay())
                    return new Response<Boolean>(false);

        return new Response<Boolean>(true);
    }
}
