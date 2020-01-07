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
public class Group {

    private Integer id;

    private String name;

    private String slogan;
}
