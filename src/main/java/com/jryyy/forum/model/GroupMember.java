package com.jryyy.forum.model;

import com.jryyy.forum.utils.group.GroupRoleCode;
import lombok.*;

import java.time.LocalDateTime;


/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class GroupMember extends IdentifiableEntity {

    private Integer groupId;

    private Integer userId;

    private String role = GroupRoleCode.MEMBER;

    private LocalDateTime date;

    private Boolean lock;

    private LocalDateTime lockDate;
}
