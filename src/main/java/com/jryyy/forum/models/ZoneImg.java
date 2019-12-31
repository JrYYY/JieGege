package com.jryyy.forum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 空间图片实体类
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneImg {
    /** id */
    private Integer id;

    /** 空间id */
    @JsonIgnore
    private Integer zoneId;

    /** 图片路径 */
    private String imgUrl;

    /** 宽 */
    private Integer width;

    /** 高 */
    private Integer height;

    /** 主题颜色 */
    private String dominantColor;
}
