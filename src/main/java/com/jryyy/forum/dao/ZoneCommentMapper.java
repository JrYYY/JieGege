package com.jryyy.forum.dao;

import com.jryyy.forum.model.response.ZoneCommentResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 评论数据库处理方式
 * @author OU
 */
@Mapper
public interface ZoneCommentMapper {

    /**
     * 更具id查找用户id
     *
     * @param id id
     * @return 用户id
     */
    @Select("select count(*) from zone_comment where zoneId = #{id} and pId is null")
    int count(@Param("id") int id);

    /**
     * 更具id查找用户id
     *
     * @param pId) id
     * @return 用户id
     */
    @Select("select count(*) from zone_comment where pId = #{pId}")
    int countReply(Integer pId);


    /**
     * 评论
     * @param userId    用户id
     * @param id        空间id
     * @param comment   评论
     * @param pId 关联id
     * @throws Exception
     */
    @Insert("insert into zone_comment(zoneId,userId,msg,pId)values(#{userId},#{id},#{comment},#{pId})")
    void insertComment(Integer userId, Integer id,String comment,Integer pId) throws Exception;

    /**
     * 确认评论是否存在
     * @param id        id
     * @return
     * @throws Exception
     */
    @Select("select id from zone_comment where id = #{id}")
    Integer findCommentById(Integer id)throws Exception;

    /**
     * 查询空间评论
     * @param zoneId    空间id
     * @param currIndex 起始页
     * @param pageSize  多少
     * @return {@link ZoneCommentResponse}
     * @throws Exception
     */
    @Select("select A.id,A.userId,B.username,A.msg comment,A.createDate from zone_comment A " +
            "join user_info B on A.userId = B.userId where zoneId = #{zoneId} and pId is null " +
            "order By A.createDate DESC limit #{currIndex},#{pageSize}")
    List<ZoneCommentResponse> findCommentByZoneId(int zoneId, int currIndex, int pageSize) throws Exception;

    /**
     * 回复
     * @param pId   关联id
     * @return  {@link ZoneCommentResponse}
     * @throws Exception
     */
    @Select("select A.id,A.userId,B.username,A.msg comment,A.createDate from zone_comment A " +
            "join user_info B on A.userId = B.userId where pId = #{pId}")
    List<ZoneCommentResponse> findReply(Integer pId)throws Exception;


    /**
     * 删除
     * @param id        id
     * @param userId    用户id
     * @throws Exception
     */
    @Delete("delete from zone_comment where id = #{id} and userId = #{userId}")
    int removeComment(int id,Integer userId) throws Exception;

    /**
     * 删除空间所有评论
     * @param zoneId    空间id
     * @throws Exception
     */
    @Delete("delete from zone_comment where zoneId = #{zoneId}")
    void removeAllComment(Integer zoneId)throws Exception;


}
