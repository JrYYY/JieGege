package com.jryyy.forum.dao;

import com.jryyy.forum.model.response.ZoneCommentResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评论数据库处理方式
 */
@Mapper
public interface ZoneCommentMapper {

    /**
     * 写入
     *
     * @param userId 用户id
     * @param zoneId 空间id
     * @param msg    信息
     * @throws Exception
     */
    @Insert("insert into zone_comment(zoneId,userId,msg)values(#{userId},#{zoneId},#{msg})")
    void insertComment(int userId, int zoneId, String msg) throws Exception;

    /**
     * @param ZoneId    空间id
     * @param currIndex 起始页
     * @param pageSize  多少
     * @return {@link ZoneCommentResponse}
     * @throws Exception
     */
    @Select("select B.emailName email,A.msg,A.createDate date from zone_comment A " +
            "join user B on A.userId = B.id where zoneId = #{zoneId} " +
            "order By A.createDate DESC " +
            "limit #{currIndex},#{pageSize}")
    List<ZoneCommentResponse> findCommentByZoneId(int ZoneId, int currIndex, int pageSize) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Delete("delete from zone_comment where id = #{id}")
    void removeComment(int id) throws Exception;


}
