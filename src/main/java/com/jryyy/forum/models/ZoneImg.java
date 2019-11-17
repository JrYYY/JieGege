package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 空间图片实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneImg {
    /* id */
    private Integer id;

    /* 空间id */
    private Integer zoneId;

    /* 图片路径 */
    private String imgUrl;

    /* 宽 */
    private Integer width;

    /* 高 */
    private Integer height;

    /* 主题颜色 */
    private String dominantColor;
}
