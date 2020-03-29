package com.jryyy.forum.dao;

import com.jryyy.forum.model.Favorites;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 收藏数据库操作
 *
 * @author OU
 */
@Mapper
public interface FavoritesMapper {

    /**
     * 插入
     *
     * @param favorites {@link Favorites}
     * @throws Exception
     */
    @Insert("insert into favorites(userId,fromUserId,title,content,createDate,modifiedDate,route,extra) " +
            "values (#{userId},#{fromUserId},#{title},#{content},now(),now(),#{route},#{extra})")
    void insertFavorites(Favorites favorites) throws Exception;

    /**
     * 删除
     *
     * @param userId 用户id
     * @param id     id
     * @return 数量
     * @throws Exception
     */
    @Delete("delete from favorites where id = #{id} and userId = #{userId}")
    int deleteFavoritesById(int userId, int id) throws Exception;

    /**
     * 查找
     *
     * @param userId 用户id
     * @return {@link Favorites}
     * @throws Exception
     */
    @Select("select id,fromUserId,title,content,createDate,modifiedDate,route,extra from favorites where userId = #{userId} order by createDate DESC")
    List<Favorites> findFavoritesByUserId(Integer userId) throws Exception;

    /**
     * 修改
     *
     * @param favorites {@link Favorites }
     * @return 修改改个数
     * @throws Exception
     */
    @Update("update favorites set fromUserId = #{fromUserId},title = #{title},content = #{content},route = #{route},modifiedDate = now(),extra = #{extra} where userId = #{userId} and id = #{id}")
    int updateFavoritesById(Favorites favorites) throws Exception;
}
