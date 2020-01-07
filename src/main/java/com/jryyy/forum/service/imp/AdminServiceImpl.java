package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.UserStatus;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.UserLock;
import com.jryyy.forum.model.response.AdminFindUserResponse;
import com.jryyy.forum.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @see com.jryyy.forum.service.AdminService
 * @author JrYYY
 */
@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

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
    public Response deleteUser(int userId) throws Exception {
        userMapper.deleteUser(userId);
        userInfoMapper.deleteUserInfo(userId);
        return new Response<>(true);
    }

    @Override
    public Response unlock(int id) throws Exception {
        userMapper.updateStatus(id, UserStatus.ACTIVE);
        redisTemplate.opsForHash().delete("userLock", id);
        return new Response();
    }

    @Override
    public Response lock(int id, int day) throws Exception {
        userMapper.updateStatus(id, UserStatus.LOCKED);
        redisTemplate.opsForHash().put("userLock", id, UserLock.builder()
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(day)).day(day).build());
        return new Response();
    }

}
