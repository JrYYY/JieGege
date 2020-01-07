package com.jryyy.forum.dao;

import com.jryyy.forum.model.response.FavoritesResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoritesMapper {
    @Select("select A.id,A.zoneId,C.username,B.createDate,B.msg " +
            "from favorites A join user_zone B on A.zoneId = B.id " +
            "join userinfo C on A.userId = C.userId Where A.userId = #{userId}")
    List<FavoritesResponse> findByUserId(int userId) throws Exception;

    @Insert("insert into favorites(userId,zoneId) values (#{userId},#{zoneId})")
    void insertFavorites(int userId, int zoneId) throws Exception;

    @Delete("delete from favorites where id = #{id}")
    void deleteById(int id) throws Exception;
}
