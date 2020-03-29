package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.FavoritesMapper;
import com.jryyy.forum.dao.UserInfoMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Favorites;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.response.FavoritesResponse;
import com.jryyy.forum.model.response.UserInfoResponse;
import com.jryyy.forum.service.FavoritesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OU
 */
@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesMapper favoritesMapper;

    private final UserInfoMapper userInfoMapper;

    @Value("${file.url}")
    private String fileUrl;

    public FavoritesServiceImpl(FavoritesMapper favoritesMapper, UserInfoMapper userInfoMapper) {
        this.favoritesMapper = favoritesMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Response favorites(Favorites favorites) throws Exception {
        favoritesMapper.insertFavorites(favorites);
        return new Response();
    }

    @Override
    public Response cancelFavorites(Integer userId, Integer id) throws Exception {
        if (favoritesMapper.deleteFavoritesById(userId, id) < 1) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();
    }

    @Override
    public Response findFavorites(Integer userId) throws Exception {
        List<Favorites> favorites = favoritesMapper.findFavoritesByUserId(userId);
        List<FavoritesResponse> responses = new ArrayList<>();
        favorites.forEach(o -> {
            FavoritesResponse response = FavoritesResponse.favoritesResponse(o);
            response.setUserInfo(UserInfoResponse.
                    userInfoResponse(userInfoMapper, o.getFromUserId(), fileUrl));
            responses.add(response);
        });
        return new Response<>(responses);
    }

    @Override
    public Response updateFavorites(Favorites favorites) throws Exception {
        if (favoritesMapper.updateFavoritesById(favorites) < 1) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();
    }
}
