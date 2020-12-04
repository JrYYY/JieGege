package com.jryyy.forum.utils.sql.bind;

/**
 * 表连接信息
 *
 * @author OU
 */
public class JoinTableInfo {

    /**
     * join sql information
     */
    private String join;

    /**
     * join type 连接类型
     */
    private JoinTypeEnum joinType;

    public JoinTableInfo(String join, JoinTypeEnum joinType) {
        this.join = join;
        this.joinType = joinType;
    }

    public JoinTableInfo() {
    }

    public static JoinTableInfoBuilder joinTableInfoBuilder() {
        return new JoinTableInfoBuilder();
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public JoinTypeEnum getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinTypeEnum joinType) {
        this.joinType = joinType;
    }

    public static class JoinTableInfoBuilder {
        private String join;
        private JoinTypeEnum joinType;

        public JoinTableInfoBuilder join(String join) {
            this.join = join;
            return this;
        }

        public JoinTableInfoBuilder joinType(JoinTypeEnum joinType) {
            this.joinType = joinType;
            return this;
        }

        public JoinTableInfo build() {
            return new JoinTableInfo(join, joinType);
        }
    }


}
