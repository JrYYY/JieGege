package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.condition.ID;

import java.lang.reflect.Field;

/**
 * 未启用
 * @author OU
 */
public class SqlGenerator<T> {

    public String findById(T date) {
        Class c = date.getClass();
        for (Field field : c.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {

            }
        }
        return null;
    }
}
