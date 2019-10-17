package com.jryyy.forum.dao;

import com.jryyy.forum.models.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户基础信息表
 */
@Mapper
public interface UserMapper {

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return {@link User}
     */
    @Select("select id,emailName,password,role from user where id = #{id}")
    User findLoginById(Integer id) throws Exception;

    /**
     * 根据name查询用户
     *
     * @param name 用户名称
     * @return {@link User}
     */
    @Select("select id,emailName,password,role,status,loginFailedAttemptCount from user where emailName = #{name}")
    User findLoginByName(@Param("name") String name) throws Exception;

    /**
     * 根据name查询id
     *
     * @param name 用户名
     * @return {@link Integer}
     * @throws Exception
     */
    @Select("select id from user where emailName = #{name}")
    Integer findIdByName(@Param("name") String name) throws Exception;


    /**
     * 创建用户
     *
     * @param user {@link User}
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user(emailName,password,role,createDate)" +
            "values(#{emailName},#{password},#{role},#{createDate})")
    void insertUser(User user) throws Exception;

    /**
     * 修改密码
     *
     * @param name     用户名
     * @param password 修改密码
     * @throws Exception
     */
    @Update("update user set password = #{password} where emailName = #{name}")
    void updatePassword(@Param("name") String name,
                        @Param("password") String password) throws Exception;

    /**
     * 更具id查找权限
     *
     * @param id 用户id
     * @return 用户权限
     * @throws Exception
     */
    @Select("select role from user where id = #{id}")
    String findIdByRole(@Param("id") int id) throws Exception;

    /**
     * 写入最大尝试次数
     *
     * @param id           用户id
     * @param AttemptCount 尝试次数
     * @throws Exception
     */
    @Update("update user set loginFailedAttemptCount = #{loginFailedAttemptCount} where id = #{id}")
    void updateLoginFailedAttemptCount(@Param("id") int id,
                                       @Param("loginFailedAttemptCount") int AttemptCount)
            throws Exception;

    /**
     * 设置用户当前状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    @Update("update user set status = #{status} where id = #{id}")
    void updateStatus(@Param("id") int id, @Param("status") String status) throws Exception;
}

