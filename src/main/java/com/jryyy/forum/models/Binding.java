package com.jryyy.forum.models;

import lombok.*;

import java.sql.Date;

/**
 * 用户绑定类
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Binding extends IdentifiableEntity{

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 绑定的用户id
     */
    private Integer boundId;

    /** 创建时间 */
    private Date createDate;

    /**
     * 绑定状态
     */
    private int status;
}
