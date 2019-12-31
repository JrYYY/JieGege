package com.jryyy.forum.services.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.FollowMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.User;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;
import com.jryyy.forum.models.response.SecurityResponse;
import com.jryyy.forum.services.UserService;
import com.jryyy.forum.utils.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @see com.jryyy.forum.services.UserService
 * @author JrYYY
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    FollowMapper followMapper;

    @Autowired
    RedisTemplate<String, String> template;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public Response userLogin(UserRequest request) throws Exception {
        request.userDoesNotExist(userMapper);
        User user = request.verifyUserLogin(userMapper);
        userMapper.updateLoginFailedAttemptCount(user.getId(), 0);
        log.info("用户：" + user.getId() + " 登入成功");
        return new Response<>(SecurityResponse.builder().
                token(tokenUtils.createJwtToken(user)).
                power(user.getRole()).userId(user.getId()).
                build());
    }

    @Override
    public Response userRegistration(UserRequestAccessRequest request) throws Exception {
        request.requestAccessPermissionDetection();
        request.verifyUserRegistered(userMapper);
        request.verifyVerificationCode(template);
        User user = request.toUser();
        try {
            userMapper.insertUser(user);
            userInfoMapper.insertUserInfo(user.getId());
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
        return new Response();
    }

    @Override
    public Response verifyUser(String email) throws Exception {
        Integer id = userMapper.findIdByName(email);
        return new Response<>(id != null);
    }

    @Override
    public Response changePassword(ForgotUsernamePasswordRequest request) throws Exception {
        request.userDoesNotExist(userMapper).verifyVerificationCode(template);
        try {
            userMapper.updatePassword(request.getName(), request.getPassword());
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

}
