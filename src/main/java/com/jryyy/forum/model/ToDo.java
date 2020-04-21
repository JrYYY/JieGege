package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务
 *
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {
    /**
     * 主键
     */
    private int id;

    /**
     * 用户 id
     */
    private int userId;

    /**
     * 目标
     */
    private String target;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改日期
     */
    private LocalDateTime modifyDate;
}
