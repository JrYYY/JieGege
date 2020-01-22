package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Check  {
    /** 签到天数 */
    private int checkInDays;

    /** 最近签到时间 */
    private LocalDate checkInDate;

    /** 联系签到天数 */
    private Integer continuousDays;
}
