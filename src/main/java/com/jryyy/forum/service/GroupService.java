package com.jryyy.forum.service;

import com.jryyy.forum.model.Group;
import com.jryyy.forum.model.GroupMember;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.GroupMemberLockRequest;
import com.jryyy.forum.model.request.GroupMemberRoleRequest;
import com.jryyy.forum.model.request.GroupRequest;

/**
 * 聊天群创建（弃用）
 * @author OU
 */

public interface GroupService {

     /**
      * 创建群组
      * @param userId    用户id
      * @param request {@link GroupRequest}
      * @return {@link Response}
      * @throws Exception
      */
     Response createGroup(Integer userId,GroupRequest request)throws Exception;

     /**
      * 解散群组
      * @param userId    用户id
      * @param groupId   群组id
      * @return {@link Response}
      * @throws Exception
      */
     Response disbandGroup(int userId,int groupId)throws Exception;

     /**
      * 添加用户
      * @param groupId   群id
      * @param userId    用户id
      * @return {@link Response}
      * @throws Exception
      */
     Response addUser(Integer groupId,Integer userId)throws Exception;

     /**
      * 设置用户权限
      * @param request {@link GroupMemberRoleRequest}
      * @return {@link Response}
      * @throws Exception
      */
     Response settingPermissions(GroupMemberRoleRequest request)throws Exception;

     /**
      * 冻结用户
      * @param request {@link GroupMemberLockRequest}
      * @return {@link Response}
      * @throws Exception
      */
     Response freezeUser(GroupMemberLockRequest request)throws Exception;

     /**
      * 解凍用戶
      * @param groupId    群组 id
      * @param id        memberId
      * @return     {@link Response}
      * @throws Exception
      */
     Response unfreezeUser(Integer groupId,Integer id)throws Exception;

     /**
      *搜索群组
      * @param info 消息
      * @return {@link Group}
      * @throws Exception
      */
     Response queryGroup(String info)throws Exception;

     /**
      * 退出群组
      * @param userId    用户id
      * @param memberId  成员id
      * @return {@link Response}
      * @throws Exception
      */
     Response leaveGroup(Integer userId,Integer memberId)throws Exception;

     /**
      * 踢出群组
      * @param groupId    群组id
      * @param id  成员id
      * @return     {@link Response}
      * @throws Exception
      */
     Response kickOutGroup(Integer groupId,Integer id)throws Exception;

     /**
      * 获取成员
      * @param groupId   群组id
      * @return          {@link GroupMember}
      * @throws Exception
      */
     Response getMember(Integer groupId)throws Exception;

     /**
      * 获取加入的群组
      * @param userId    用户id
      * @return          {@link Group}
      * @throws Exception
      */
     Response getGroup(Integer userId)throws Exception;
}
