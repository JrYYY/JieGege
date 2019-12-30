package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZoneImgResponse {
    /* id */
    private Integer id;

    /* 图片路径 */
    private String imgUrl;

    /* 宽 */
    private Integer width;

    /* 高 */
    private Integer height;

    /* 主题颜色 */
    private String dominantColor;
}