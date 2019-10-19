package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserZone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.models.response.ZoneResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
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

    /**
     * 删除单个空间所有图片
     *
     * @param zoneId 空间id
     * @throws Exception
     */
    @Delete("delete from zone_img where zoneId = #{zoneId}")
    void deleteAllZoneImg(@Param("zoneId") int zoneId) throws Exception;

    /**
     * 删除单个图片
     *
     * @param id
     * @throws Exception
     */
    @Delete("delete from zone_img where id = #{id}")
    void deleteZoneImgById(@Param("id") Integer id) throws Exception;

    /**
     * 查询单个空间所有图片
     *
     * @param zoneId 空间id
     * @return {@link ZoneImg}
     * @throws Exception
     */
    @Select("select id,zoneId,imgUrl,width,height from zone_img where zoneId = #{zoneId}")
    List<ZoneImg> findAllImgByZoneId(@Param("zoneId") int zoneId) throws Exception;

    /**
     * 添加图片
     *
     * @param zoneImg
     * @throws Exception
     */
    @Insert("insert into zone_img(zoneId,imgUrl,width,height) values (#{zoneId},#{imgUrl},#{width},#{height})")
    void insertZoneImg(ZoneImg zoneImg) throws Exception;


    @Select("select id,msg,createDate date,msgType,praise from user_zone ")
    List<ZoneResponse> findZoneByDate() throws Exception;


    List<ZoneResponse> findZoneByApproval() throws Exception;
}
