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
public class GroupMemberLockRequest {

    @NotNull(message = "groupId 不能为空")
    private Integer groupId;

    @NotNull(message = "id 不能为空")
    private Integer memberId;

    @NotNull(message = "冻结天数不能为空")
    private Integer day;


}
