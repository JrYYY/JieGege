package com.jryyy.forum.services.imp;

import com.jryyy.forum.dao.UserFriendMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.BadCredentialsException;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.UserFriend;
import com.jryyy.forum.models.response.UserFriendResponse;
import com.jryyy.forum.services.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("UserFriendService")
public class UserFriendServiceImp implements UserFriendService {

    @Autowired
    UserFriendMapper userFriendMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response viewWatchlist(int userId) throws Exception {
        try {
            return new Response<List<UserFriendResponse>>(userFriendMapper.findAttentionBasedOnId(userId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查看关注列表失败");
        }
    }

    @Override
    public Response viewFanList(int fenId) throws Exception {
        try {
            return new Response<List<UserFriendResponse>>(userFriendMapper.findFansBasedOnId(fenId));
        } catch (Exception e) {
            throw new RuntimeException("查看粉丝列表失败");
        }
    }

    @Override
    public Response attention(int userId, String email) throws Exception {
        System.out.println(email);
        Integer friendId = userMapper.findIdByName(email);
        if (friendId == null)
            throw new EntityNotFoundException("该用户不存在");
        if (userFriendMapper.findFriend(userId, friendId) != null)
            throw new BadCredentialsException("已关注,该用户");
        try {
            userFriendMapper.insertUserFriend(UserFriend.builder().
                    userId(userId).friendId(friendId).
                    build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加好友失败");
        }
    }

    @Override
    public Response takeOff(int userId, int id) throws Exception {
        UserFriend userFriend = userFriendMapper.findFriendsBasedOnId(id);
        if (userFriend == null)
            throw new EntityNotFoundException("异常访问");
        if (!userFriend.getUserId().equals(userId))
            throw new EntityNotFoundException("没有关注，无法取消！");
        try {
            userFriendMapper.unsubscribe(userId, id);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("取消失败");
        }

    }

    @Override
    public Response judgedHasBeenConcerned(int userId, String email) throws Exception {
        Integer followId = userMapper.findIdByName(email);
        if (followId == null)
            throw new EntityNotFoundException("该用户不存在");
        try {
            boolean judged = userFriendMapper.findFriend(userId, followId) != null;
            return new Response<Boolean>(judged);
        } catch (Exception e) {
            throw new RuntimeException("服务器错误");
        }
    }

}
