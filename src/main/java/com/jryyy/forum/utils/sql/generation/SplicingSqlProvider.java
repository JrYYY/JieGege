package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.exception.SqlInfoException;
import com.jryyy.forum.utils.sql.model.BaseModel;
import org.apache.ibatis.jdbc.SQL;

public class SplicingSqlProvider {

    public String findMultipleData(BaseModel baseModel) throws SqlInfoException, IllegalAccessException {
        Class model = baseModel.getDateClass();
        String sql = GenerateSqlStringUtil.start(new SQL(), model, baseModel.getData())
                .column().table().join().where().group().order().toString();
        System.out.println(sql);
        return sql;
    }

}
