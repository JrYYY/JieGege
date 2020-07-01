package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.UserStatus;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.dao.ZoneCommentMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.UserLock;
import com.jryyy.forum.model.response.AdminFindUserResponse;
import com.jryyy.forum.model.response.PaginationResponse;
import com.jryyy.forum.model.response.UserInfoResponse;
import com.jryyy.forum.model.response.ZoneCommentResponse;
import com.jryyy.forum.service.AdminService;
import com.jryyy.forum.utils.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @see com.jryyy.forum.service.AdminService
 * @author JrYYY
 */
@Slf4j
@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final ZoneCommentMapper zoneCommentMapper;
    private final TokenUtils tokenUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${file.url}")
    private String fileUrl;

    public AdminServiceImpl(UserMapper userMapper, UserInfoMapper userInfoMapper, ZoneCommentMapper zoneCommentMapper, TokenUtils tokenUtils, RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
        this.zoneCommentMapper = zoneCommentMapper;
        this.tokenUtils = tokenUtils;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response findAllUsers() throws Exception {
        try {
            List<AdminFindUserResponse> allUsers = userMapper.findAllUsers();
            return new Response<>(allUsers);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response findComment(int currIndex, int pageSize) throws Exception {
        List<ZoneCommentResponse> responses = zoneCommentMapper.findComment(currIndex, pageSize);
        responses.forEach(comment -> {
            UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(comment.getUserId());
            userInfo.addAvatarUrl(fileUrl);
            comment.setUserInfo(userInfo);
        });
        return new Response<>(new PaginationResponse<>(responses, zoneCommentMapper.countAll(), currIndex, pageSize));
    }

    @Override
    public Response findCommentByUserId(Integer userId, int currIndex, int pageSize) throws Exception {
        List<ZoneCommentResponse> responses = zoneCommentMapper.findCommentByUserId(userId, currIndex, pageSize);
        responses.forEach(comment -> {
            UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(comment.getUserId());
            userInfo.addAvatarUrl(fileUrl);
            comment.setUserInfo(userInfo);
        });
        return new Response<>(new PaginationResponse<>(responses, zoneCommentMapper.countByUserId(userId), currIndex, pageSize));
    }

    @Override
    public Response deleteUser(int userId) throws Exception {
        userMapper.deleteUser(userId);
        userInfoMapper.deleteUserInfo(userId);
        return new Response<>(true);
    }

    @Override
    public Response unlock(Integer id) throws Exception {
        userMapper.updateStatus(id, UserStatus.ACTIVE);
        redisTemplate.opsForHash().delete("userLock", id.toString());
        return new Response<>("解冻成功");
    }

    @Override
    public Response lock(Integer id, Integer day) throws Exception {
        userMapper.updateStatus(id, UserStatus.LOCKED);
        redisTemplate.opsForHash().put("userLock", id.toString(), UserLock.builder()
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(day)).day(day).build().toString());
        tokenUtils.deleteJwtToken(id);
        return new Response<>("冻结成功");
    }

}
