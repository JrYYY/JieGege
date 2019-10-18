package com.jryyy.forum.dao;

import com.jryyy.forum.models.Binding;
import com.jryyy.forum.models.response.BindingResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 关联信息表操作
 */
@Mapper
public interface BindingMapper {

    /**
     * 根据userId 查询关联的用户信息
     *
     * @param userId 用户id
     * @return {@link BindingResponse} 返回关联列表
     */
    @Select("select A.id,B.username,B.avatar,A.createDate date " +
            "from binding A join userinfo B " +
            "on A.boundId = B.userId " +
            "where A.userId = #{userId} and status = 1")
    List<BindingResponse> selectBindingUserInfo(@Param("userId") Integer userId) throws Exception;

    /**
     * 查看用户是否已绑定
     *
     * @param userId  用户id
     * @param boundId 被关联用户id
     * @return {@link Binding}
     * @throws Exception
     */
    @Select("select userId from binding where userId = #{userId} and boundId = #{boundId}")
    Integer findIdByBinding(@Param("userId") int userId, @Param("boundId") int boundId) throws Exception;

    /**
     * 确认用户
     *
     * @param boundId 绑定用户id
     * @param id      绑定id
     * @return
     */
    @Select("select Id from binding where boundId = #{boundId} and id = #{id} and status = 0")
    Integer confirmUser(@Param("boundId") int boundId, @Param("id") int id);

    /**
     * 插入关联信息
     *
     * @param binding {@link Binding}
     * @throws Exception
     */
    @Insert("insert into binding(userId,boundId) values(#{userId},#{boundId})")
    void insertBinding(Binding binding) throws Exception;

    /**
     * 设置绑定状态
     *
     * @param boundId
     * @param id
     * @throws Exception
     */
    @Update("update binding set status = 1 where boundId = #{boundId} and id = #{id} ")
    void updateBindingStatus(@Param("boundId") int boundId, @Param("id") int id) throws Exception;

    /**
     * 删除关联信息
     *
     * @param userId
     * @param id
     * @throws Exception
     */
    @Delete("delete from binding where userId = #{userId} and id = #{id}")
    void deleteBinding(@Param("userId") int userId, @Param("id") int id) throws Exception;

    /**
     * 拒绝绑定
     *
     * @param boundId
     * @param id
     * @throws Exception
     */
    @Delete("delete from binding where boundId = #{boundId} and id = #{id} and status = 0")
    void deleteBindingBoundId(@Param("boundId") int boundId, @Param("id") int id) throws Exception;
}
