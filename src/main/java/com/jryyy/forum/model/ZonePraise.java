package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonePraise {

    /**
     *用户id
     */
    int userId;

    /**
     * 用户名
     */
    String username;

    /**
     * 头像
     */
    String avatar;
}
