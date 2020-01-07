package com.jryyy.forum.utils;

import com.jryyy.forum.model.IdentifiableEntity;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OU
 */
public class BaseSqlProviderTool<T extends IdentifiableEntity> {




    private List<String> getColumnNames(T data){
        Class c = data.getClass();
        Field[] fields = c.getDeclaredFields();
        List<String> columns = new ArrayList<>();
        for (Field field : fields) {
            columns.add(field.getName());
//            if(field.isAnnotationPresent()){
//
//            }
        }
        return columns;
    }


    public String selectSql(T data){
        return new SQL(){


        }.toString();
    }
}
