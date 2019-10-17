package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户绑定类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Binding {
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
    private Integer boundId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 绑定状态
     */
    private int status;
}
