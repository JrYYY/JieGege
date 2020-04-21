package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 单词记忆精度
 *
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordProgress {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 每日任务
     */
    private Integer dailyDuty;

    /**
     * 今日完成进度
     */
    private Integer currentDailyPosition;

    /**
     * 总进度
     */
    private Long currentPosition;

    /**
     * 所有任务
     */
    private Integer total;

    /**
     * 当前天数
     */
    private LocalDate modifyDate;
}
