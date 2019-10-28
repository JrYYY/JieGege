package com.jryyy.forum.services.imp;

import com.jryyy.forum.constant.status.GlobalStatus;
import com.jryyy.forum.dao.UserFriendMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.User;
import com.jryyy.forum.models.UserFriend;
import com.jryyy.forum.services.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserFriendService")
public class UserFriendServiceImp implements UserFriendService {

    @Autowired
    UserFriendMapper userFriendMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response viewWatchlist(int userId) throws Exception {
        try {
            return new Response<>(userFriendMapper.findAttentionBasedOnId(userId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response viewFanList(int fenId) throws Exception {
        try {
            return new Response<>(userFriendMapper.findFansBasedOnId(fenId));
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response attention(int userId, int id) throws Exception {
        User user = userMapper.findLoginById(id);
        if (user == null)
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        if (userFriendMapper.findFriend(userId, id) != null)
            throw new GlobalException(GlobalStatus.alreadyConcerned);
        try {
            userFriendMapper.insertUserFriend(UserFriend.builder().
                    userId(userId).friendId(id).build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response takeOff(int userId, int id) throws Exception {
        UserFriend userFriend = userFriendMapper.findFriendsBasedOnId(id);
        if (userFriend == null || !userFriend.getUserId().equals(userId))
            throw new GlobalException(GlobalStatus.noAttention);
        try {
            userFriendMapper.unsubscribe(userId, id);
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }

    }

    @Override
    public Response judgedHasBeenConcerned(int userId, String email) throws Exception {
        Integer followId = userMapper.findIdByName(email);
        if (followId == null)
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        try {
            boolean judged = userFriendMapper.findFriend(userId, followId) != null;
            return new Response<>(judged);
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

}
