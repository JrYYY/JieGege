package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.condition.Id;

import java.lang.reflect.Field;

/**
 * 未启用
 * @author OU
 */
public class SqlGenerator {

    public String findById(Object condition){
        Class c = condition.getClass();
        for (Field field : c.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)){

            }
        }
        return null;
    }
}
