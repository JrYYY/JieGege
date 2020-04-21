package com.jryyy.forum.dao;

import com.jryyy.forum.model.RiskTech;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 风控数据存取
 */
@Mapper
public interface RiskTechMapper {


    /**
     * 添加举报
     *
     * @param userId 用户id
     * @param riskId 风险id
     * @param reason 原因
     * @return 添加数量
     * @throws` Exception
     */
    @Insert("insert into risk_tech(userId,riskId,reason) values (#{userId},#{riskId},#{reason})")
    int insertRisk(int userId, int riskId, String reason) throws Exception;

    /**
     * 更改
     *
     * @param id id
     * @return 更改数量
     * @throws Exception
     */
    @Update("update risk_tech set examination = 1 where id = #{id}")
    int updateRisk(int id) throws Exception;


    @SelectProvider(type = SqlProvider.class, method = "selectPersonSql")
    List<RiskTech> findRisks(RiskTech riskTech) throws Exception;

    class SqlProvider {
        public String selectPersonSql(RiskTech riskTech) {
            return new SQL() {{
                SELECT("id", "userId", "riskId", "examination", "reason", "createDate");
                FROM("risk_tech").WHERE("1 = 1");
                if (riskTech.getId() != null) {
                    WHERE(" and id = #{id}");
                }
                if (riskTech.getUserId() != null) {
                    WHERE(" and userId = #{userId}");
                }
                if (riskTech.getRiskId() != null) {
                    WHERE(" and riskId = #{riskId}");
                }
                if (riskTech.getCreateDate() != null) {
                    WHERE(" and createDate = #{createDate}");
                }
                if (riskTech.getExamination() != null) {
                    WHERE("and examination = #{examination}");
                }
            }}.toString();
        }
    }

}
