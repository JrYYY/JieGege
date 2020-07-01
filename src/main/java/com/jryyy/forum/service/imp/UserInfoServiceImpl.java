package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.dao.FollowMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.ZonePraiseMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Check;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.UserInfo;
import com.jryyy.forum.model.request.UserInfoRequest;
import com.jryyy.forum.model.response.UserInfoResponse;
import com.jryyy.forum.service.UserInfoService;
import com.jryyy.forum.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * @author OU
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    private static final String username_regular = "";

    private final FollowMapper followMapper;

    private final FileUtils fileUtils;

    private static  final String DEFAULT = "0";
    private final ZonePraiseMapper zonePraiseMapper;

    @Value("${file.url}")
    private String fileUrl;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper, FollowMapper followMapper,
                               ZonePraiseMapper zonePraiseMapper, FileUtils fileUtils) {
        this.userInfoMapper = userInfoMapper;
        this.followMapper = followMapper;
        this.zonePraiseMapper = zonePraiseMapper;
        this.fileUtils = fileUtils;
    }

    @Override
    public Response viewUserInfo(Integer userId) throws Exception {
        try {
            consecutiveCheckIn(userId);
            UserInfo userInfo = userInfoMapper.selectUserInfo(userId);
            if (!userInfo.getAvatar().equals(DEFAULT)) {
                userInfo.setAvatar(fileUrl + userInfo.getAvatar());
            }
            if (!userInfo.getBgImg().equals(DEFAULT)) {
                userInfo.setBgImg(fileUrl + userInfo.getBgImg());
            }
            userInfo.setFollowers(followMapper.followersQuantityByUserId(userId));
            userInfo.setFollowing(followMapper.followingQuantityByUserId(userId));
            userInfo.setLikes(zonePraiseMapper.countByUserId(userId));
            return new Response<>(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response changeUserInfo(UserInfoRequest request) throws Exception {
        log.info(request.toString());
        try {
            userInfoMapper.updateUserInfo(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new Response();
    }

    @Override
    public Response searchUser(String info) throws Exception {
        List<UserInfoResponse> responses = userInfoMapper.findInfoByCondition(info);
        responses.forEach(o->o.addAvatarUrl(fileUrl));
        return new Response<>(responses);
    }

    @Override
    public Response updateAvatar(Integer userId, MultipartFile avatar) throws Exception {
        userInfoMapper.updateUserAvatar(userId, fileUtils.savePicture(KayOrUrl.userAvatarUrl(userId),avatar));
        return new Response();
    }

    @Override
    public Response updateBgImg(Integer userId, MultipartFile bgImg) throws Exception {
        userInfoMapper.updateUserBgImg(userId,fileUtils.saveTalkImg(KayOrUrl.userBgImgUrl(userId),bgImg));
        return new Response();
    }

    @Override
    public Response updateUsername(Integer userId, String username) throws Exception {
        return null;
    }

    @Override
    public Response checkIn(Integer userId) throws Exception {
        checkedIn(userId);
        consecutiveCheckIn(userId);
        Check check = userInfoMapper.selectCheckIn(userId);
        userInfoMapper.checkIn(userId,check.getCheckInDays()+1,check.getContinuousDays()+1);
        log.info(userId+": 签到成功");
        return new Response<>("签到获得" + signCredit(userId) + "积分");
    }

    @Override
    public Response initializeImage(Integer userId) throws Exception {
        userInfoMapper.updateUserAvatar(userId,DEFAULT);
        return new Response();
    }

    private void checkedIn(Integer userId)throws Exception{
        Check check = userInfoMapper.selectCheckIn(userId);
        if(check.getCheckInDate().equals(LocalDate.now())){
            log.info(check.getCheckInDate().plusDays(1).toString());
            throw new GlobalException(GlobalStatus.alreadySignedIn);
        }
    }

    private void consecutiveCheckIn(Integer userId)throws Exception{
        Check check = userInfoMapper.selectCheckIn(userId);
        if (check.getCheckInDate() != null && check.getCheckInDate().plusDays(1).isBefore(LocalDate.now())) {
            log.info(check.getCheckInDate().plusDays(1).toString());
            userInfoMapper.modifyContinuousCheckIn(userId);
        }
    }

    private int signCredit(Integer userId) throws Exception {
        Check check = userInfoMapper.selectCheckIn(userId);
        int credit = check.getContinuousDays() < 3 ? check.getContinuousDays() : 3;
        userInfoMapper.updateCredit(userId, userInfoMapper.findCreditByUserId(userId) + credit);
        return credit;
    }

}
