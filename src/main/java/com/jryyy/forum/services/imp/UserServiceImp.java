package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.UserFriendMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.User;
import com.jryyy.forum.models.request.ForgotUsernamePasswordRequest;
import com.jryyy.forum.models.request.UserRequest;
import com.jryyy.forum.models.request.UserRequestAccessRequest;
import com.jryyy.forum.models.response.UserResponse;
import com.jryyy.forum.services.UserService;
import com.jryyy.forum.tool.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserFriendMapper userFriendMapper;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public Response userLogin(UserRequest request) throws Exception {
        request.userDoesNotExist(userMapper);
        User user = request.verifyUserLogin(userMapper);
        Integer followers = userFriendMapper.followersNumByUId(user.getId());
        Integer following = userFriendMapper.followingTotalByFId(user.getId());
        userMapper.updateLoginFailedAttemptCount(user.getId(), 0);
        return new Response<UserResponse>(UserResponse.builder().
                token(tokenUtils.createJwtToken(user)).
                power(user.getRole()).
                followersNumber(followers).
                followingNumber(following).
                build());
    }

    @Override
    public Response userRegistration(UserRequestAccessRequest request) throws Exception {
        request.requestAccessPermissionDetection();
        request.verifyUserRegistered(userMapper);
        User user = request.toUser();
        try {
            userMapper.insertUser(user);
            userInfoMapper.insertUserInfo(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("注册用户失败");
        }
        return new Response();
    }

    @Override
    public Response changePassword(ForgotUsernamePasswordRequest request) throws Exception {
        request.userDoesNotExist(userMapper);
        //request.verification();
        try {
            userMapper.updatePassword(request.getName(), request.getPassword());
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("修改密码失败");
        }
    }


}
