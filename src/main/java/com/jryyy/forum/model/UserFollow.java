package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 关注|关注者
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollow {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 关注的id
     */
    private Integer friendId;

    /**
     * 创建时间
     */
    private Date createDate;
}
