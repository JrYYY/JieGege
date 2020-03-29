package com.jryyy.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @JsonIgnore
    private Integer userId;

    @NotNull(message = "来源用户id不能为空")
    private Integer fromUserId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类容
     */
    @NotNull(message = "内容不能为空")
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更改时间
     */
    private LocalDateTime modifiedDate;

    /**
     * 路由
     */
    private String route;

    /**
     * 配置信息
     */
    private String extra;
}
