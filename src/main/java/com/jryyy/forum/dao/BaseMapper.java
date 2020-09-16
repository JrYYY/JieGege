package com.jryyy.forum.dao;

import com.jryyy.forum.model.IdentifiableEntity;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface BaseMapper<T extends IdentifiableEntity> {
}
