package com.jryyy.forum.controller;

import com.jryyy.forum.model.Favorites;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.FavoritesService;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author OU
 */
@RestController
@UserLoginToken
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    /**
     * 收藏
     *
     * @param userId  用户id
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/{userId}")
    public Response favorites(@PathVariable Integer userId, @ModelAttribute @Valid Favorites favorites) throws Exception {
        favorites.setUserId(userId);
        return favoritesService.favorites(favorites);
    }

    /**
     * 取消收藏
     *
     * @param userId 用户id
     * @param id     id
     * @return {@link Response}
     * @throws Exception
     */
    @DeleteMapping("/{userId}")
    public Response cancelFavorites(@PathVariable Integer userId, @RequestParam Integer id) throws Exception {
        return favoritesService.cancelFavorites(userId, id);
    }

    /**
     * 查询自己的收藏
     *
     * @param userId 用户id
     * @return {@link com.jryyy.forum.model.Favorites}
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public Response findFavorites(@PathVariable Integer userId) throws Exception {
        return favoritesService.findFavorites(userId);
    }

    /**
     * 更改
     *
     * @param userId  用户id
     * @param id      id
     * @param content 内容
     * @return {@link Response}
     * @throws Exception
     */
    @PutMapping("/{userId}")
    public Response updateFavorites(@PathVariable Integer userId, @ModelAttribute @Valid Favorites favorites) throws Exception {
        favorites.setUserId(userId);
        return favoritesService.updateFavorites(favorites);
    }
}
