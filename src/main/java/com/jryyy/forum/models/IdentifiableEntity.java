package com.jryyy.forum.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 具有id属性的所有实体的基类
 * @author JrYYY
 */
@Getter
@Setter
@ToString
public class IdentifiableEntity {

    /** id */
    private Integer id;

}
