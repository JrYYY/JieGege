package com.jryyy.forum.service;

import com.jryyy.forum.model.Favorites;
import com.jryyy.forum.model.Response;

/**
 * @author OU
 */
public interface FavoritesService {

    /**
     * 收藏
     *
     * @param favorites {@link Response}
     * @return {@link Response}
     * @throws Exception
     */
    Response favorites(Favorites favorites) throws Exception;

    /**
     * 取消收藏
     *
     * @param userId 用户id
     * @param id     id
     * @return {@link Response}
     * @throws Exception
     */
    Response cancelFavorites(Integer userId, Integer id) throws Exception;

    /**
     * 查询自己的收藏
     *
     * @param userId 用户id
     * @return {@link com.jryyy.forum.model.Favorites}
     * @throws Exception
     */
    Response findFavorites(Integer userId) throws Exception;

    /**
     * 修改
     *
     * @param favorites {@link Favorites}
     * @return 数量
     * @throws Exception
     */
    Response updateFavorites(Favorites favorites) throws Exception;
}
