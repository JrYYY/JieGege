package com.jryyy.forum.dao;

import com.jryyy.forum.model.IdentifiableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface BaseMapper<T extends IdentifiableEntity> {



}
