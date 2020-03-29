package com.jryyy.forum.model.response;


import com.jryyy.forum.model.Favorites;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏夹响应
 *
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritesResponse {
    /**
     * id
     */
    private Integer id;


    /**
     * 标题
     */
    private String title;

    /**
     * 类容
     */
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

    /**
     * 用户信息
     * {@link UserInfoResponse}
     */
    private UserInfoResponse userInfo;

    public static FavoritesResponse favoritesResponse(Favorites favorites) {
        return FavoritesResponse.builder()
                .id(favorites.getId())
                .title(favorites.getTitle())
                .content(favorites.getContent())
                .route(favorites.getRoute())
                .extra(favorites.getExtra())
                .createDate(favorites.getCreateDate())
                .modifiedDate(favorites.getModifiedDate())
                .build();
    }

}
