package com.jryyy.forum.model.response;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.dao.UserInfoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    /**
     * id
     */
    private int userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * bio
     */
    private String bio;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 最近登入时间
     */
    private LocalDateTime recentLogin;

    /**
     * 最近状态
     */
    private String recentState;


    public void addAvatarUrl(String url){
        if(!this.avatar.equals(Constants.DEFAULT)) {
            this.avatar = url + this.avatar;
        }
    }

    public static UserInfoResponse userInfoResponse(UserInfoMapper userInfoMapper, Integer userId, String fileUrl) {
        UserInfoResponse userInfo = userInfoMapper.findInfoByUserId(userId);
        userInfo.addAvatarUrl(fileUrl);
        return userInfo;
    }

    public static UserInfoResponse userInfoResponse(UserInfoMapper userInfoMapper, Integer userId) {
        return userInfoMapper.findInfoByUserId(userId);
    }

}
