package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.exception.SqlInfoException;
import com.jryyy.forum.utils.sql.model.BaseModel;
import org.apache.ibatis.jdbc.SQL;

public class SqlGenerator {

    public String selectSql(BaseModel baseModel) throws SqlInfoException {
        String sql = new SQL() {{
            Class model = baseModel.getDateClass();
            //添加查询直段
            SqlComponentUtil.getColumn(model).forEach(this::SELECT);
            //查询表
            FROM(SqlComponentUtil.getTableName(model));
            //join

            SqlComponentUtil.getJoinTableInfo(model).forEach(a -> {
                switch (a.getJoinType()) {
                    case JOIN:
                        JOIN(a.getJoin());
                        break;
                    case LEFT_JOIN:
                        LEFT_OUTER_JOIN(a.getJoin());
                        break;
                    case RIGHT_JOIN:
                        RIGHT_OUTER_JOIN(a.getJoin());
                        break;
                    case OUTER_JOIN:
                        OUTER_JOIN(a.getJoin());
                        break;
                }
            });
            WHERE("b.username = #{data.username}");

        }}.toString();
        System.out.println(sql);
        return sql;
    }


}
