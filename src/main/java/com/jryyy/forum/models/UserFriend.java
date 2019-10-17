package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFriend {
    /**
     * 绑定序列id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 绑定的用户id
     */
    private Integer friendId;

    /**
     * 创建时间
     */
    private String createDate;
}
