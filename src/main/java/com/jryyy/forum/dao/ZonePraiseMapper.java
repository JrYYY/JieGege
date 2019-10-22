package com.jryyy.forum.dao;

import com.jryyy.forum.models.response.ZonePraiseResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    void deleteZonePraise(int userId, int zoneId) throws Exception;

    /**
     * @param zoneId 空间id
     * @throws Exception
     */
    @Delete("delete from zone_praise where zoneId = #{zoneId}")
    void deleteAllZonePraise(int zoneId) throws Exception;

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
     * @return {@link ZonePraiseResponse}
     * @throws Exception
     */
    @Select("select B.username,C.emailName email from (zone_praise A join userinfo B on A.userId = B.userId) join user C on A.userId = C.id where A.zoneId = #{zoneId}")
    List<ZonePraiseResponse> selectZonePraise(int zoneId) throws Exception;

    /**
     *  验证是否点赞
     * @param userId    用户id
     * @param zoneId    空间id
     * @return userId
     * @throws Exception
     */
    @Select("select userId from zone_praise where userId = #{userId} and zoneId = #{zoneId}")
    Integer findPraiseByUser(int userId, int zoneId) throws Exception;

    /**
     * 统计
     *
     * @param zoneId id
     * @return 点赞数
     * @throws Exception
     */
    @Select("select count(*) from zone_praise where zoneId = #{zoneId}")
    int countZonePraise(int zoneId) throws Exception;
}
