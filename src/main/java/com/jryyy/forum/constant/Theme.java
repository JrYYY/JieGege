package com.jryyy.forum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 主题
 *
 * @author OU
 */
@ToString
@Getter
@AllArgsConstructor
public enum Theme {
    /**
     * 不同的主题色
     */
    AWL("AWL,", "阿瓦隆水鸭"),
    DEF("DEF", "原始"),
    KLS("KLS", "卡鲁斯的选择"),
    JSRC("JSRC", "金属日出"),
    DQZM("DQZM", "大气之芒"),
    ICECREAM("ICECREAM", "蓝莓冰激凌"),
    ROSE("ROSE", "玫瑰星云"),
    COAL("COAL", "煤黑 AMOLED");

    private String theme;
    private String themeName;
}
