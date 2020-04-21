package com.jryyy.forum.dao;

import com.jryyy.forum.model.ToDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface ToDoMapper {

    @Insert("insert to_do(userId,target,createDate,modifyDate) values(#{userId},#{target},now(),now())")
    int insertToDo(Integer userId, String target) throws Exception;

    @Update("update to_do set target = #{target},modifyDate = now() where id = #{id} and userId = #{userId}")
    int updateToDo(Integer userId, Integer id, String target) throws Exception;

    @Delete("delete from to_do where id = #{id} and userId = #{userId}")
    int deleteToDo(Integer userId, Integer id) throws Exception;

    @Select("select id,userId,target,createDate,modifyDate from to_do where userId = #{userId}")
    List<ToDo> findTargetByUserId(Integer userId) throws Exception;

}
