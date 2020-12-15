package com.jryyy.forum.utils.sql.generation;


import com.jryyy.forum.utils.sql.model.BaseModel;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 未启用 test
 *
 * @author OU
 */
public interface MybatisBaseRepository<T> {


    @SelectProvider(type = SplicingSqlProvider.class, method = "findMultipleData")
    List<T> findDataByInfo(BaseModel<T> baseModel) throws Exception;


    T findDataById() throws Exception;

}
