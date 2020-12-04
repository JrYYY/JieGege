package com.jryyy.forum.utils.sql.generation;


import com.jryyy.forum.utils.sql.model.BaseModel;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 未启用
 *
 * @author OU
 */
public interface BaseSql<T> {

    @SelectProvider(type = SqlGenerator.class, method = "selectSql")
    List<T> findById(BaseModel<T> baseModel) throws Exception;


}
