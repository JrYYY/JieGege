package com.jryyy.forum.dao;

import com.jryyy.forum.models.IdentifiableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BaseMapper<T extends IdentifiableEntity> {

    @Select("select * from #{table}")
    T selectAll(String table);

    @Select("select * from #{table} where id = #{id}")
    T findDataById(String table, T data);


    void deleteData(String table, Integer id);

    class SqlProvider {


    }
}
