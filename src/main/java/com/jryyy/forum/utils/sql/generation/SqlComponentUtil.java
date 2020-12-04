package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.bind.*;
import com.jryyy.forum.utils.sql.exception.SqlInfoException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL 生产工具
 *
 * @author OU
 */
public class SqlComponentUtil {

    /**
     * 列
     *
     * @param model 数据模型
     * @return 返回查询列
     */
    public static List<String> getColumn(Class model) {
        Field[] columnNames = model.getDeclaredFields();
        List<String> columns = getColumn(columnNames);
        return columns.size() == 0 ? getAllColumn(columnNames) : columns;
    }


    /**
     * 获取注解列
     *
     * @param columns 所有列
     * @return 返回查询列
     */
    public static List<String> getColumn(Field[] columns) {
        List<String> columnNames = new ArrayList<>();
        for (Field column : columns) {
            if (column.isAnnotationPresent(Column.class)) {
                Column col = column.getAnnotation(Column.class);
                String columnName = col.value();
                if (columnName.isEmpty()) {
                    columnNames.add(column.getName());
                } else {
                    columnNames.add(columnName + " " + column.getName());
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取所有列名
     *
     * @param columns 所有列
     * @return 返回所有列名
     */
    public static List<String> getAllColumn(Field[] columns) {
        List<String> columnNames = new ArrayList<>();
        for (Field column : columns) {
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    /**
     * 获取表
     *
     * @param model 数据模型
     * @return 表名称
     * @throws SqlInfoException {@link SqlInfoException} 反写sql异常信息
     */
    public static String getTableName(Class model) throws SqlInfoException {
        if (model.isAnnotationPresent(Table.class)) {
            Table table = (Table) model.getAnnotation(Table.class);
            if (table.value().isEmpty()) {
                throw new SqlInfoException("table 表别名称不能为空");
            } else if (table.tableAlias().isEmpty()) {
                return table.value();
            } else {
                return table.value() + " " + table.tableAlias();
            }
        } else {
            throw new SqlInfoException("@Table 注解未标注");
        }
    }

    /**
     * join 连接 sql
     *
     * @param model 数据模型
     * @return {@link JoinTableInfo}
     * @throws SqlInfoException {@link SqlInfoException} 反写sql异常信息
     */
    public static List<JoinTableInfo> getJoinTableInfo(Class model) throws SqlInfoException {
        List<JoinTableInfo> joinTableInfos = new ArrayList<>();
        if (model.isAnnotationPresent(Join.class)) {
            Join join = (Join) model.getAnnotation(Join.class);
            String[] tableNames = join.value();
            JoinTypeEnum[] joinTypes = join.joinType();
            if (tableNames.length == 0) {
                throw new SqlInfoException("join 连接表名不能为NULL");
            }
            if (joinTypes.length == tableNames.length) {
                for (int i = 0; i < tableNames.length; i++) {
                    joinTableInfos.add(JoinTableInfo.joinTableInfoBuilder().
                            join(tableNames[i]).joinType(joinTypes[i]).build());
                }
            } else {
                throw new SqlInfoException("join 连接表名、连接表别名、连接条件 数量不对等");
            }
        }
        return joinTableInfos;
    }

}
