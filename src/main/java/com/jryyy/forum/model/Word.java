package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    /**
     * id
     */
    private Integer id;

    /**
     * 英文
     */
    private String english;

    /**
     * 中文
     */
    private String chinese;

    /**
     * 权重
     */
    private int weight;
}