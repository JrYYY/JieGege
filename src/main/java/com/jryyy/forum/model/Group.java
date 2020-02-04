package com.jryyy.forum.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author OU
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Group extends IdentifiableEntity{

    private String name;

    private String slogan;

    private Integer userId;

    private String avatar;

    private LocalDateTime createDate;
}
