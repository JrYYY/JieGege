package com.jryyy.forum.controller;

import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.request.GroupMemberLockRequest;
import com.jryyy.forum.model.request.GroupMemberRoleRequest;
import com.jryyy.forum.model.request.GroupRequest;
import com.jryyy.forum.service.GroupService;
import com.jryyy.forum.utils.group.GroupPermissions;
import com.jryyy.forum.utils.group.GroupRoleCode;
import com.jryyy.forum.utils.security.UserLoginToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author OU
 */
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) { this.groupService = groupService; }

    @GetMapping("/group/query")
    public Response queryGroup(String info) throws Exception {
        return groupService.queryGroup(info);
    }

    @PostMapping("/group/add")
    public Response addUser(@RequestParam Integer userId,@RequestParam Integer groupId) throws Exception {
        return groupService.addUser(groupId, userId);
    }

    @UserLoginToken
    @PostMapping("/group/{userId}")
    public Response createGroup(@PathVariable Integer userId, @ModelAttribute GroupRequest request) throws Exception {
        return groupService.createGroup(userId, request);
    }


    @UserLoginToken
    @DeleteMapping("/group/{userId}")
    public Response deleteGroup(@PathVariable Integer userId,@RequestParam Integer groupId) throws Exception {
        return groupService.disbandGroup(userId, groupId);
    }


    @GetMapping("/group/member")
    public Response getMember(@RequestParam Integer groupId) throws Exception {
        return groupService.getMember(groupId);
    }

    @UserLoginToken
    @GetMapping("/user/group/{userId}")
    public Response getGroup(@PathVariable Integer userId) throws Exception {
        return groupService.getGroup(userId);
    }

    @UserLoginToken
    @DeleteMapping("/member/admin/{userId}")
    @GroupPermissions(notAllowed = GroupRoleCode.MEMBER)
    public Response kickUser(@PathVariable Integer userId, @RequestParam Integer groupId, @RequestParam Integer memberId) throws Exception {
        return groupService.kickOutGroup(groupId, memberId);
    }

    @UserLoginToken
    @DeleteMapping("/member/{userId}")
    @GroupPermissions(notAllowed = GroupRoleCode.FOUNDER)
    public Response leaveGroup(@PathVariable Integer userId,@RequestParam Integer memberId) throws Exception {
        return groupService.leaveGroup(userId, memberId);
    }

    @UserLoginToken
    @PutMapping("/member/role/{userId}")
    @GroupPermissions(permission = GroupRoleCode.FOUNDER)
    public Response settingPermissions(@PathVariable Integer userId, @ModelAttribute GroupMemberRoleRequest request) throws Exception {
        return groupService.settingPermissions(request);
    }

    @UserLoginToken
    @PutMapping("/member/lock/{userId}")
    @GroupPermissions(notAllowed = GroupRoleCode.MEMBER)
    public Response lock(@PathVariable Integer userId, @ModelAttribute GroupMemberLockRequest request) throws Exception {
        return groupService.freezeUser(request);
    }

    @UserLoginToken
    @PutMapping("/member/unlock/{userId}")
    @GroupPermissions(notAllowed = GroupRoleCode.MEMBER)
    public Response unlock(@PathVariable Integer userId, @RequestParam Integer groupId, @RequestParam Integer memberId) throws Exception {
        return groupService.unfreezeUser(groupId, memberId);
    }

}

