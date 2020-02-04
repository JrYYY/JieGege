package com.jryyy.forum.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoritesMapper {

    @Insert("insert into favorites(userId,zoneId) values (#{userId},#{zoneId})")
    void insertFavorites(int userId, int zoneId) throws Exception;

    @Delete("delete from favorites where id = #{id}")
    void deleteById(int id) throws Exception;
}
