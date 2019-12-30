package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.UserFriendMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.models.Check;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.request.UserInfoRequest;
import com.jryyy.forum.models.response.CheckResponse;
import com.jryyy.forum.models.response.UserInfoResponse;
import com.jryyy.forum.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;

@Service("UserInfoService")
@Slf4j
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserFriendMapper userFriendMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserZoneMapper zoneMapper;

    @Override
    public Response viewOtherPeopleSPersonalInformation(String email) throws Exception {
        Integer id = userMapper.findIdByName(email);
        return viewUserPersonalInformation(id);
    }

    @Override
    public Response viewUserPersonalInformation(int userId) throws Exception {
        try {
            UserInfoResponse response = userInfoMapper.selectUserInfo(userId);
            Integer following = userFriendMapper.followingTotalByFId(userId);
            Integer followers = userFriendMapper.followersNumByUId(userId);
            Integer zoneNum = zoneMapper.countZoneNumByUserId(userId);
            response.setFollowingNum(following);
            response.setFollowersNum(followers);
            response.setZoneNum(zoneNum);
            return new Response<>(response);
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
        Check check = userInfoMapper.selectCheckIn(userId);
        Response<CheckResponse> response = whetherToCheckIn(userId);
        if (response.getData().isStatus())
            throw new PreconditionFailedException("已签到，签到失败！");
        try {
            Date date = new Date();
            System.out.println(check.getCheckInDays());
            userInfoMapper.checkIn(userId, check.getCheckInDays() + 1,
                    check.getContinuousDays() + 1);
            return new Response<>(CheckResponse.builder().status(true).
                    checkInDate(new java.sql.Date(date.getYear(), date.getMonth(), date.getDay())).
                    checkInDays(check.getCheckInDays() + 1).
                    continuousDays(check.getContinuousDays() + 1).
                    build());
        } catch (Exception e) {
            throw new RuntimeException("服务器错误");
        }
    }

    @Override
    public Response whetherToCheckIn(int userId) throws Exception {
        Check check = userInfoMapper.selectCheckIn(userId);

        CheckResponse response = CheckResponse.builder()
                .checkInDays(check.getCheckInDays())
                .checkInDate(check.getCheckInDate())
                .continuousDays(check.getContinuousDays())
                .build();

        if (check.getCheckInDate() == null) {
            response.setStatus(false);
            return new Response<>(response);
        }

        Date date = new Date();
        Date checkDate = new Date(check.getCheckInDate().getYear(),
                check.getCheckInDate().getMonth(), check.getCheckInDate().getDate());
        int days = differentDays(checkDate,
                new Date(date.getYear(), date.getMonth(), date.getDate()));

        log.info(userId + ":" + days + "天没有签到");

        if (days == 0)
            response.setStatus(true);
        else if (days == 1)
            response.setStatus(false);
        else {
            userInfoMapper.modifyContinuousCheckIn(userId);
            response.setStatus(false);
        }
        return new Response<>(response);
    }


    /**
     * date2比date1多的天数
     *
     * @param date1 {@link Data}
     * @param date2 {@link Data}
     * @return 天数
     */
    public int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal1.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

    private int days(Date date) {
        Calendar cal = Calendar.getInstance();
        int[] months = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        System.out.println(year + "-" + month + "-" + day);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            months[2] = 29;
        for (int i = 1; i < month; i++)
            day += months[i];
        return day;
    }

}
