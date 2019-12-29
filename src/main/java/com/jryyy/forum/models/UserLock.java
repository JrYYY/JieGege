package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLock {

    /**
     * 冻结起始天
     */
    private LocalDate startDate;

    /**
     * 冻结结束天
     */
    private LocalDate endDate;

    /**
     * 冻结天数
     */
    private int day;
}
