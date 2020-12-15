package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.bind.AND;
import com.jryyy.forum.utils.sql.bind.Condition;
import com.jryyy.forum.utils.sql.bind.JoinTable;
import com.jryyy.forum.utils.sql.bind.OR;
import com.jryyy.forum.utils.sql.exception.SqlInfoException;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * SQL 生成工具
 *
 * @author OU
 */
public class GenerateSqlStringUtil {

    private static ReflectionModelInfo modelInfo;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * sql 构建器
     */
    private SQL sql;
    /**
     * 数据模型
     */
    private Class model;
    /**
     * 数据
     */
    private Object data;

    public GenerateSqlStringUtil(SQL sql, Class model, Object data) {
        this.sql = sql;
        this.model = model;
        this.data = data;
        modelInfo = new ReflectionModelInfo(model, data);
    }

    public static GenerateSqlStringUtil start(SQL sql, Class model, Object data) {
        return new GenerateSqlStringUtil(sql, model, data);
    }

    public String toString() {
        return sql.toString();
    }

    /**
     * 列
     *
     * @return this
     */
    public GenerateSqlStringUtil column() {
        modelInfo.getColumnNames().forEach(sql::SELECT);
        return this;
    }


    /**
     * 获取表
     *
     * @return this
     * @throws SqlInfoException {@link SqlInfoException} 反写sql异常信息
     */
    public GenerateSqlStringUtil table() throws SqlInfoException {
        sql.FROM(modelInfo.getTableName());
        return this;
    }

    /**
     * 连接
     *
     * @return this
     * @throws SqlInfoException {@link SqlInfoException} 反写sql 连接时异常信息
     */
    public GenerateSqlStringUtil join() throws SqlInfoException {
        for (JoinTable joinTable : modelInfo.getJoinTables()) {
            switch (joinTable.getJoinType()) {
                case JOIN:
                    sql.JOIN(joinTable.getJoin());
                    break;
                case LEFT_JOIN:
                    sql.LEFT_OUTER_JOIN(joinTable.getJoin());
                    break;
                case RIGHT_JOIN:
                    sql.RIGHT_OUTER_JOIN(joinTable.getJoin());
                    break;
                case OUTER_JOIN:
                    sql.OUTER_JOIN(joinTable.getJoin());
                    break;
            }
        }
        return this;
    }

    public GenerateSqlStringUtil where() throws SqlInfoException, IllegalAccessException {
        return where(model.getDeclaredFields());
    }


    private GenerateSqlStringUtil where(Field[] columns) throws SqlInfoException, IllegalAccessException {
        for (Field column : columns) {
            try {
                if (column.isAnnotationPresent(AND.class)) {
                    if (!column.isAnnotationPresent(Condition.class)) {
                        throw new SqlInfoException("@AND  无法单独使用,要配合 @Condition 使用");
                    }
                    sql.AND();
                    condition(column);
                } else if (column.isAnnotationPresent(OR.class)) {
                    if (!column.isAnnotationPresent(Condition.class)) {
                        throw new SqlInfoException("@OR 无法单独使用,要配合 @Condition 使用");
                    }
                    sql.OR();
                    condition(column);
                } else if (column.isAnnotationPresent(Condition.class)) {
                    condition(column);
                }
            } catch (IllegalAccessException e) {
                log.error(column.getName() + " 解析有误", e);
                throw e;
            }
        }
        return this;
    }


    private void condition(Field column) throws IllegalAccessException, SqlInfoException {
        Condition condition = column.getAnnotation(Condition.class);
        String sqlSb;
        switch (condition.operation().getCode()) {
            case 0:
                sqlSb = modelInfo.splicingDefaultSql(column);
                break;
            case 2:
                sqlSb = modelInfo.splicingMultipleRowsSql(column);
                break;
            case 1:
                sqlSb = modelInfo.splicingSingleRowsSql(column);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + condition.operation().getMsg());
        }
        splicingSqlCondition(sqlSb, column);
    }


    private void splicingSqlCondition(String sqlSb, Field column) throws IllegalAccessException {
        Condition condition = column.getAnnotation(Condition.class);
        if (condition.notNull()) {
            sql.WHERE(sqlSb);
        } else {
            try {
                column.setAccessible(true);
                Object columnDate = column.get(data);
                if (columnDate != null) {
                    sql.WHERE(sqlSb);
                }
            } catch (IllegalAccessException e) {
                log.error("#sql error:" + sqlSb, e);
                throw e;
            }
        }
    }


    public GenerateSqlStringUtil group() {
        modelInfo.getGroupColumnName().forEach(sql::GROUP_BY);
        return this;
    }

    public GenerateSqlStringUtil order() {
        modelInfo.getOrderColumnName().forEach(sql::ORDER_BY);
        return this;
    }
}

