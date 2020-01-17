package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.model.request.GroupMemberLockRequest;
import com.jryyy.forum.model.request.GroupMemberRoleRequest;
import com.jryyy.forum.utils.group.GroupRoleCode;
import com.jryyy.forum.dao.GroupMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Group;
import com.jryyy.forum.model.GroupMember;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.GroupRequest;
import com.jryyy.forum.service.GroupService;
import com.jryyy.forum.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OU
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;

    private final UserMapper userMapper;

    private final FileUtils fileUtils;

    /**
     * 网络访问资源文件夹
     */
    @Value("${file.url}")
    private String staticAccessPath;

    private static final String GROUP_IMAGE_PATH = "/img/group/";

    private static final int LEAST_MODIFIED_PARAMETERS = 1;

    public GroupServiceImpl(GroupMapper groupMapper, UserMapper userMapper, FileUtils fileUtils) {
        this.groupMapper = groupMapper;
        this.userMapper = userMapper;
        this.fileUtils = fileUtils;
    }

    @Override
    public Response queryGroup(String info) throws Exception {
        try {
            return new Response<>(groupMapper.findGroupByInfo(info));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response createGroup(Integer userId, GroupRequest request) throws Exception {
        Group group = request.toGroup();
        group.setAvatar(fileUtils.savePicture(GROUP_IMAGE_PATH, request.getAvatar()));
        group.setUserId(userId);
        groupMapper.insertGroup(group);
        groupMapper.insertGroupMember(GroupMember.builder().groupId(group.getId())
                .userId(group.getUserId()).role(GroupRoleCode.FOUNDER).build());
        for (Integer id : request.getMembers()) {
            if (userMapper.findUserById(id) == null) {
                throw new GlobalException(GlobalStatus.userDoesNotExist);
            }
            groupMapper.insertMember(group.getId(), id);
        }
        return new Response<>();
    }

    @Override
    public Response disbandGroup(int userId, int groupId) throws Exception {
        if (groupMapper.deleteGroup(groupId, userId) < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        groupMapper.deleteMembers(groupId);
        return new Response();
    }

    @Override
    public Response addUser(Integer groupId, Integer userId) throws Exception {
        if (userJoinedGroup(groupId, userId)) {
            throw new GlobalException(GlobalStatus.userHasJoinedTheGroup);
        }
        try {
            groupMapper.insertGroupMember(GroupMember.builder()
                    .groupId(groupId).userId(userId).role(GroupRoleCode.MEMBER).build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response leaveGroup(Integer userId, Integer memberId) throws Exception {
        if (groupMapper.deleteMember(memberId, userId) < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();
    }

    @Override
    public Response settingPermissions(GroupMemberRoleRequest request) throws Exception {
        if(request.getRole().equals(GroupRoleCode.FOUNDER)){
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        }
        if (groupMapper.updateMember(request.getGroupId(), request.getMemberId(), request.getRole())
                < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();
    }

    @Override
    public Response freezeUser(GroupMemberLockRequest request) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(request.getDay());
        log.info(dateTime+"");
        if (groupMapper.lock(request.getGroupId(), request.getMemberId(), dateTime)
                < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();

    }

    @Override
    public Response unfreezeUser(Integer groupId, Integer id) throws Exception {
        if (groupMapper.unlock(groupId, id) < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        }
        return new Response();

    }

    @Override
    public Response kickOutGroup(Integer groupId, Integer id) throws Exception {
        GroupMember groupMember = groupMapper.findMemberByUserId(groupId, id);
        if (groupMember == null || !groupMember.getRole().equals(GroupRoleCode.MEMBER)) {
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        }
        if (groupMapper.deleteMemberById(groupId, id) < LEAST_MODIFIED_PARAMETERS) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        }
        return new Response();
    }

    @Override
    public Response getMember(Integer groupId) throws Exception {
        return new Response<>(groupMapper.findMemberByGroupId(groupId));
    }

    @Override
    public Response getGroup(Integer userId) throws Exception {
        List<Group> groups = groupMapper.findGroupById(userId);
        groups.forEach(o -> o.setAvatar(staticAccessPath + o.getAvatar()));
        return new Response<>(groups);
    }

    private Boolean userJoinedGroup(Integer groupId, Integer userId) throws Exception {
        try {
            GroupMember groupMember = groupMapper.findMemberByUserId(groupId, userId);
            return groupMember != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }


}
