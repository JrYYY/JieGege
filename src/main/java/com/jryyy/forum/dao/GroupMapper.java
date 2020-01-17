package com.jryyy.forum.dao;

import com.jryyy.forum.model.Group;
import com.jryyy.forum.model.GroupMember;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface GroupMapper {

    /**
     * 根据信息查找群组
     *
     * @param info 信息
     * @return {@link Group}
     * @throws Exception
     */
    @Select("select * from group_info where name like concat('%', #{info}, '%') or id like concat('%', #{info}, '%')")
    List<Group> findGroupByInfo(String info) throws Exception;

    /**
     * 查找用户参加的群组
     *
     * @param id 用户id
     * @return {@link Group}
     * @throws Exception
     */
    @Select("select A.id,name,slogan,createDate,A.userId,avatar from group_info A join group_member B on A.id = B.Id where B.userId = #{id}")
    @Results({@Result(property = "createDate", column = "createDate", jdbcType = JdbcType.TIMESTAMP)})
    List<Group> findGroupById(@Param("id") Integer id) throws Exception;

    /**
     * 查找群组成员
     *
     * @param id 群组id
     * @return {@link GroupMember}
     * @throws Exception
     */
    @Select("select id,groupId,userId,role,date from group_member where groupId = #{id}")
    @Results({@Result(property = "date", column = "date", jdbcType = JdbcType.TIMESTAMP)})
    List<GroupMember> findMemberByGroupId(@Param("id") Integer id) throws Exception;

    /**
     * 确认用户是否存在
     *
     * @param groupId 群组id
     * @param userId  用户id
     * @return {@link GroupMember}
     * @throws Exception
     */
    @Select("select id,groupId,userId,role,date,status,lockDate from group_member where groupId = #{groupId} and userId = #{userId}")
    @Results({@Result(property = "date", column = "date", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "lockDate", column = "lockDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "lock",column = "status", jdbcType = JdbcType.BIT)})
    GroupMember findMemberByUserId(@Param("groupId") Integer groupId, @Param("userId") Integer userId) throws Exception;


    /**
     * 群组初始化
     *
     * @param group {@link Group}
     * @throws Exception
     */
    @Insert("insert into group_info(name,slogan,userId,avatar)values(#{name},#{slogan},#{userId},#{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertGroup(Group group) throws Exception;

    /**
     * 删除群组
     *
     * @param id     id
     * @param userId 用户id
     * @return 删除的行数
     * @throws Exception
     */
    @Delete("delete from group_info where id = #{id} and userId = #{userId}")
    int deleteGroup(@Param("id") Integer id, @Param("userId") Integer userId) throws Exception;

    /**
     * 解散群组
     * @param groupId   群组id
     * @throws Exception
     */
    @Delete("delete from group_member where groupId = #{groupId}")
    void deleteMembers(@Param("groupId") Integer groupId)throws Exception;

    /**
     * 添加成员
     *
     * @param groupMember {@link GroupMember}
     * @throws Exception
     */
    @Insert("insert into group_member(groupId,userId,date,role) values(#{groupId},#{userId},now(),#{role})")
    void insertGroupMember(GroupMember groupMember) throws Exception;

    /**
     * 修改用户权限
     * @param groupId   群组id
     * @param id        id
     * @param role      角色\
     * @return          修改个数
     * @throws Exception
     */
    @Update("update group_member set  role = #{role} where id = #{id} and groupId = #{groupId}")
    int updateMember(@Param("groupId") Integer groupId,
                      @Param("id") Integer id,
                      @Param("role") String role) throws Exception;

    /**
     * 拒接添加
     *
     * @param id        编号
     * @param userId    用户id
     * @return          清除数目
     * @throws Exception
     */
    @Delete("delete from group_member where id = #{id} and userId = #{userId}")
    int deleteMember(@Param("id") Integer id, @Param("userId") Integer userId) throws Exception;

    /**
     * 踢出用户
     * @param groupId   群组id
     * @param id    id
     * @return      清除数目
     * @throws Exception
     */
    @Delete("delete from group_member where id = #{id} and groupId = #{groupId}")
    int deleteMemberById( @Param("groupId") Integer groupId,@Param("id") Integer id) throws Exception;


    /**
     * 查找用户权限
     * @param userId     用户 id
     * @param groupId   群组id
     * @return          role
     * @throws Exception
     */
    @Select("select role from group_member where userId = #{userId} and groupId = #{groupId}")
    String findMemberByIdAndGroupId(Integer userId, Integer groupId) throws Exception;

    /**
     * 冻结
     * @param groupId   群组id
     * @param id        member Id
     * @param dateTime  冻结到的时间
     * @return          是否修改成功
     * @throws Exception
     */
    @Update("update group_member set status = 1,lockDate = #{dateTime} where id = #{id}")
    int lock(Integer groupId,Integer id,LocalDateTime dateTime) throws Exception;

    /**
     * 解冻
     * @param groupId   群组id
     * @param id        memberId
     * @return          是否成功
     * @throws Exception
     */
    @Update("update group_member set  status = 0 where id = #{id} ")
    int unlock(Integer groupId,int id) throws Exception;

    /**
     * 添加用户
     * @param groupId   群组id
     * @param userId    用户id
     */
    @Insert("insert into group_member(groupId,userId)values(#{groupId},#{userId})")
    void insertMember(Integer groupId,Integer userId);


}
