package com.jryyy.forum.dao;

import com.jryyy.forum.model.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface DiaryMapper {

    @Insert("insert diary(userId,content,createDate,modifyDate) values(#{userId},#{content},now(),now())")
    int insertDiary(Integer userId, String content) throws Exception;

    @Update("update diary set content = #{content},modifyDate = now() where id = #{id} and userId = userId")
    int updateDiary(Integer userId, Integer id, String content) throws Exception;

    @Delete("delete from diary where id = #{id} and userId = userId")
    int deleteDiary(Integer userId, Integer id) throws Exception;

    @Select("select id,userId,content,createDate,modifyDate from diary where userId = #{userId}")
    List<Diary> findDiaryByUserId(Integer userId) throws Exception;
}
