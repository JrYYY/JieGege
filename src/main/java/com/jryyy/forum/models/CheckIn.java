package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckIn {
    /* 签到天数 */
    private int checkInDays;

    /* 最近签到时间 */
    private Date checkInDate;
}
