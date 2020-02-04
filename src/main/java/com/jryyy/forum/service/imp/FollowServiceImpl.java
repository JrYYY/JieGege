package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.FollowMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.User;
import com.jryyy.forum.model.UserFollow;
import com.jryyy.forum.model.response.FollowResponse;
import com.jryyy.forum.service.FollowService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author OU
 * @see FollowService
 */
@Service("UserFriendService")
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    private final UserMapper userMapper;

    private static  final String DEFAULT = "0";

    @Value("${file.url}")
    private String fileUrl;

    public FollowServiceImpl(FollowMapper followMapper, UserMapper userMapper) {
        this.followMapper = followMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Response viewWatchlist(int userId) throws Exception {
        try {
            List<FollowResponse>  responses = followMapper.findAttentionBasedOnId(userId);
            responses.forEach(o-> {if(!o.getAvatar().equals(DEFAULT)){o.setAvatar(fileUrl + o.getAvatar());}});
            return new Response<>(responses);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response viewFanList(int fenId) throws Exception {
        try {
            List<FollowResponse>  responses = followMapper.findFansBasedOnId(fenId);
            responses.forEach(o-> {if(!o.getAvatar().equals(DEFAULT)){o.setAvatar(fileUrl + o.getAvatar());}});
            return new Response<>(responses);
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response attention(int userId, int id) throws Exception {
        User user = userMapper.findUserById(id);
        if (user == null) {
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        if (followMapper.findFriend(userId, id) != null) {
            throw new GlobalException(GlobalStatus.alreadyConcerned);
        }
        try {
            followMapper.insertUserFriend(UserFollow.builder()
                    .userId(userId).friendId(id).build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response takeOff(int userId, int id) throws Exception {
        if (followMapper.findFriend(userId,id) == null) {
            throw new GlobalException(GlobalStatus.noAttention);
        }
        try {
            followMapper.unsubscribe(userId, id);
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }

    }

    @Override
    public Response judgedHasBeenConcerned(int userId, Integer id) throws Exception {
        try {
            boolean judged = followMapper.findFriend(userId, id) != null;
            return new Response<>(judged);
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

}
