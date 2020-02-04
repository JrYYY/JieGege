package com.jryyy.forum.dao;

import com.jryyy.forum.model.ZonePraise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface ZonePraiseMapper {

    /**
     * 取消赞
     *
     * @param userId 用户id
     * @param zoneId 空间id
     * @throws Exception
     */
    @Delete("delete from zone_praise where userId = #{userId} and zoneId = #{zoneId}")
    int deleteZonePraise(int userId, int zoneId) throws Exception;

    /**
     * 删除空间所有赞
     * @param zoneId 空间id
     * @throws Exception
     */
    @Delete("delete from zone_praise where zoneId = #{zoneId}")
    void deleteAllZonePraise(Integer zoneId) throws Exception;

    /**
     * 点赞
     *
     * @param userId 用户id
     * @param zoneId 空间id
     * @throws Exception
     */
    @Insert("insert into zone_praise(userId,zoneId)values(#{userId},#{zoneId})")
    void insertZonePraise(int userId, int zoneId) throws Exception;

    /**
     * 点赞列表
     *
     * @param zoneId    id
     * @return {@link ZonePraise}
     * @throws Exception
     */
    @Select("select userId from zone_praise where zoneId = #{zoneId}")
    List<Integer> selectZonePraise(int zoneId);

    /**
     * 统计
     *
     * @param zoneId id
     * @return 点赞数
     * @throws Exception
     */
    @Select("select count(*) from zone_praise where zoneId = #{zoneId}")
    int countZonePraise(int zoneId) throws Exception;

    /**
     * 是否点赞
     * @param userId    用户id
     * @param zoneId    空间id
     * @return 是否点赞
     */
    @Select("select userId from zone_praise where userId = #{userId} and zoneId = #{zoneId}")
    Integer isFavorite(int userId, int zoneId);
}
