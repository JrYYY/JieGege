package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayOrUrl;
import com.jryyy.forum.dao.FollowMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.User;
import com.jryyy.forum.model.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.model.request.UserRequest;
import com.jryyy.forum.model.request.UserRequestAccessRequest;
import com.jryyy.forum.model.response.SecurityResponse;
import com.jryyy.forum.service.UserService;
import com.jryyy.forum.utils.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @see com.jryyy.forum.service.UserService
 * @author JrYYY
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserInfoMapper userInfoMapper;

    private final FollowMapper followMapper;

    private  final RedisTemplate<String, String> template;

    private final TokenUtils tokenUtils;

    public UserServiceImpl(UserMapper userMapper, UserInfoMapper userInfoMapper, FollowMapper followMapper, RedisTemplate<String, String> template, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
        this.followMapper = followMapper;
        this.template = template;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public Response userLogin(UserRequest request) throws Exception {
        request.userDoesNotExist(userMapper);
        User user = request.verifyUserLogin(userMapper);
        try {
            userMapper.updateLoginFailedAttemptCount(user.getId(), 0);
            log.info("用户：" + user.getId() + " 登入成功");
            return new Response<>(SecurityResponse.builder().
                    token(tokenUtils.createJwtToken(user)).
                    role(user.getRole()).userId(user.getId()).
                    build());
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }

    }

    @Override
    public Response userRegistration(UserRequestAccessRequest request) throws Exception {
        request.requestAccessPermissionDetection();
        request.verifyUserRegistered(userMapper);
        request.verifyVerificationCode(template);
        User user = request.toUser();
        try {
            userMapper.insertUser(user);
            userInfoMapper.insertUserInfo(user.getId(),user.getEmailName());
            template.delete(KayOrUrl.registrationCodeKey(request.getName()));
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
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
            userMapper.updatePassword(request.getName(), request.getPwd());
            template.delete(KayOrUrl.modifyPasswordCodeKey(request.getName()));
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

}
