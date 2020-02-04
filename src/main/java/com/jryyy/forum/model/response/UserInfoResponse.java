package com.jryyy.forum.model.response;

import com.jryyy.forum.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


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


    public void addAvatarUrl(String url){
        if(!this.avatar.equals(Constants.DEFAULT)) {
            this.avatar = url + this.avatar;
        }
    }

}
