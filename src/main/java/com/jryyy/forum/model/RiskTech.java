package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 风控数据模型
 *
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskTech {
    /**
     * key
     */
    private Integer id;

    /**
     * 举报用户id
     */
    private Integer userId;

    /**
     * 风险存在用户id
     */
    private Integer riskId;

    /**
     * 原因
     */
    private String reason;

    /**
     * 审查
     */
    private Boolean examination;

    /**
     * 举报时间
     */
    private LocalDateTime createDate;
}
