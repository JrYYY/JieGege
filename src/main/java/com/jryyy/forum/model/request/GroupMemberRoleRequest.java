package com.jryyy.forum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberRoleRequest {
    @NotNull(message = "groupId 不能为空")
    private Integer groupId;

    @NotNull(message = "id不能为空")
    private Integer memberId;

    @NotNull(message = "角色不能为空")
    private String role;
}
