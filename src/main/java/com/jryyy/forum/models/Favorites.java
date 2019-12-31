package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorites {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 动态id
     */
    private Integer zoneId;
}
