package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserZone;
import org.apache.ibatis.annotations.*;

/**
 * 用户空间dao
 */
@Mapper
public interface UserZoneMapper {
    /**
     * 根据用户id查找空间
     *
     * @param userId 用户id
     * @return {@link UserZone}
     * @throws Exception
     */
    @Select("select id,msg,createDate,msgType from user_zone where userId = #{userId}")
    UserZone findZoneByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 写说说
     *
     * @param userZone {@link UserZone}
     * @throws Exception
     */
    @Insert("insert into user_zone(userId,msg,msgType)values(#{userId},#{msg},#{msgType})")
    void insertZone(UserZone userZone) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Delete("delete from user_zone where id = #{id}")
    void deleteZone(@Param("id") int id) throws Exception;
}
