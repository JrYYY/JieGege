package com.jryyy.forum.dao;

import com.jryyy.forum.model.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author OU
 */
@Mapper
public interface GroupMapper {

    /**
     * 群组初始化
     * @param group  {@link Group}
     * @throws Exception
     */
    @Insert("insert group into (name,slogan) values(#{name},#{slogan})")
    void insertGroup(Group group)throws Exception;


}
