package com.jryyy.forum.utils.sql.generation;

import com.jryyy.forum.utils.sql.bind.*;
import com.jryyy.forum.utils.sql.exception.SqlInfoException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射模型类信息
 *
 * @author OU
 */
public class ReflectionModelInfo {

    /**
     * 数据模型
     */
    private Class model;

    /**
     * 数据
     */
    private Object data;


    public ReflectionModelInfo(Class model, Object data) {
        this.model = model;
        this.data = data;
    }

    /**
     * 列
     *
     * @return 返回查询列
     */
    public List<String> getColumnNames() {
        Field[] columns = model.getDeclaredFields();
        List<String> columnNames = getAllQueryColumn(columns);
        if (columnNames.size() == 0) {
            columnNames = getAllColumn(columns);
        }
        return columnNames;
    }


    /**
     * 获取注解列
     *
     * @param columns 所有列
     * @return 获取字段状态
     */
    private List<String> getAllQueryColumn(Field[] columns) {
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
     * 获取所有行直段
     *
     * @param columns 行
     * @return 查询列名
     */
    private List<String> getAllColumn(Field[] columns) {
        List<String> columnNames = new ArrayList<>();
        for (Field column : columns) {
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    /**
     * 获取表
     *
     * @return 表名称
     * @throws SqlInfoException {@link SqlInfoException} 反写sql异常信息
     */
    public String getTableName() throws SqlInfoException {
        if (model.isAnnotationPresent(Table.class)) {
            Table table = (Table) model.getAnnotation(Table.class);
            if (table.value().isEmpty()) {
                throw new SqlInfoException("table 表名称不能为空");
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
     * 例：{@link @Join("user_info b on user.ID = b.userId") } join user_info b on user.ID = b.userId
     * {@link @Join(value = "user_info b on user.ID = b.userId",)}
     *
     * @return {@link List<JoinTable>} 连接表信息
     * @throws SqlInfoException {@link SqlInfoException} 反写sql异常信息
     */
    public List<JoinTable> getJoinTables() throws SqlInfoException {
        List<JoinTable> joinTables = new ArrayList<>();
        if (model.isAnnotationPresent(Join.class)) {
            Join join = (Join) model.getAnnotation(Join.class);
            String[] tableNames = join.value();
            JoinTypeEnum[] joinTypes = join.joinType();
            if (tableNames.length == 0) {
                throw new SqlInfoException("join 连接表名不能为NULL");
            }
            if (joinTypes.length == tableNames.length) {
                for (int i = 0; i < tableNames.length; i++) {
                    joinTables.add(JoinTable.Builder().join(tableNames[i])
                            .joinType(joinTypes[i]).build());
                }
            } else if (joinTypes.length == 1) {
                for (String tableName : tableNames) {
                    joinTables.add(JoinTable.Builder().join(tableName)
                            .joinType(joinTypes[0]).build());
                }
            } else {
                throw new SqlInfoException("join 连接表名、连接表别名、连接条件 数量不对等");
            }
        }
        return joinTables;
    }

    /**
     * 反射默认 条件sql
     * 例：
     * {@link @Condition(value = "b.username = #{data.useranme}")
     * private string username
     * }
     * 返回数据 ： b.username = #{data.username}
     *
     * @param column 列
     * @return 判单条件
     */
    public String splicingDefaultSql(Field column) {
        Condition condition = column.getAnnotation(Condition.class);
        OperationEnum operation = condition.operation();
        return condition.value() + " " + operation.getSql();
    }

    /**
     * 反射单行 条件sql
     * 例：
     * {@link @Condition(value = "b.username ",operation = OperationEnum.EQUAL)
     * private string username
     * }
     * 返回数据 ： b.username = #{data.username}
     *
     * @param column 列
     * @return 判单条件
     */
    public String splicingSingleRowsSql(Field column) {
        Condition condition = column.getAnnotation(Condition.class);
        OperationEnum operation = condition.operation();
        String sqlSb = condition.value() + " " + operation.getSql();
        if (operation.equals(OperationEnum.LIKE)) {
            sqlSb += " CONCAT('%',#{data." + column.getName() + "},'%')";
        } else if (operation.equals(OperationEnum.LEFT_LIKE)) {
            sqlSb += " CONCAT('%',#{data." + column.getName() + "},'')";
        } else if (operation.equals(OperationEnum.RIGHT_LIKE)) {
            sqlSb += " CONCAT('',#{data." + column.getName() + "},'%')";
        } else {
            sqlSb += " #{data." + column.getName() + "}";
        }
        return sqlSb;
    }

    /**
     * 反射单行 条件sql
     * 例：
     * {@link @Condition(value = "b.username ",operation = OperationEnum.IN)
     * private List<String> username
     * }
     * 返回数据 ：
     * b.username IN
     * <foreach collection="list" item="username" index="index" open="(" close=")" separator=",">
     * #{username}
     * </foreach>
     *
     * @param column 列
     * @return 判单条件
     */
    public String splicingMultipleRowsSql(Field column) throws IllegalAccessException, SqlInfoException {
        Condition condition = column.getAnnotation(Condition.class);
        OperationEnum operation = condition.operation();
        String sqlSb = condition.value() + " " + operation.getSql();
        column.setAccessible(true);
        if (column.get(data) instanceof String) {
            sqlSb += " (#{data." + column.getName() + "})";
        } else if (column.get(data) instanceof Object[] || column.get(data) instanceof List) {
            sqlSb += "<foreach collection='data." + column.getName() + "' item='data." + column.getName() + "' index='index'  open='(' separator=',' close=')' > " +
                    "#{data." + column.getName() + "}" +
                    "</foreach>";
        } else {
            throw new SqlInfoException("@Condition" + operation.getSql() + "引用该类型");
        }
        return sqlSb;
    }

    /**
     * 获取分组 列名
     *
     * @return 分组列名称
     */
    public List<String> getGroupColumnName() {
        List<String> groupColumns = new ArrayList<>();
        Field[] columns = model.getDeclaredFields();
        if (model.isAnnotationPresent(Group.class)) {
            Group group = (Group) model.getAnnotation(Group.class);
            groupColumns.addAll(Arrays.asList(group.value()));
        }
        for (Field column : columns) {
            if (column.isAnnotationPresent(Group.class)) {
                Group group = column.getAnnotation(Group.class);
                if (group.value().length != 0) {
                    groupColumns.addAll(Arrays.asList(group.value()));
                } else {
                    groupColumns.add(column.getName());
                }
            }
        }
        return groupColumns;
    }

    /**
     * @return
     */
    public List<String> getOrderColumnName() {
        List<String> orderColumns = new ArrayList<>();
        Field[] columns = model.getDeclaredFields();
        if (model.isAnnotationPresent(Order.class)) {
            Order order = (Order) model.getAnnotation(Order.class);
            List<String> orderInfo = Arrays.asList(order.value());
            if (order.isDesc())
                orderInfo.forEach(a -> a += " desc");
            orderColumns.addAll(orderInfo);
        }
        for (Field column : columns) {
            if (column.isAnnotationPresent(Order.class)) {
                Order order = column.getAnnotation(Order.class);
                if (order.value().length != 0) {
                    List<String> orderInfo = Arrays.asList(order.value());
                    if (order.isDesc())
                        orderInfo.forEach(a -> a += " DESC");
                    orderColumns.addAll(orderInfo);
                } else {
                    if (order.isDesc()) {
                        orderColumns.add(column.getName() + " DESC");
                    } else {
                        orderColumns.add(column.getName());
                    }
                }

            }
        }
        return orderColumns;
    }
}
