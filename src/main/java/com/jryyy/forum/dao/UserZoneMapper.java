package com.jryyy.forum.dao;

import com.jryyy.forum.models.UserZone;
import com.jryyy.forum.models.ZoneImg;
import com.jryyy.forum.models.response.ZoneImgResponse;
import com.jryyy.forum.models.response.ZoneResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 用户空间dao
 */
@Mapper
public interface UserZoneMapper {

    /**
     * 统计所有
     *
     * @return 总量
     * @throws Exception
     */
    @Select("select count(*) from user_zone")
    Integer countZoneNum() throws Exception;

    /**
     * 更具用户id统计
     *
     * @param userId 用户id
     * @return 用户上传数量
     * @throws Exception
     */
    @Select("select count(*) from user_zone where userId = #{userId}")
    Integer countZoneNumByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 更具id查找用户id
     *
     * @param id id
     * @return 用户id
     * @throws Exception
     */
    @Select("select userId from user_zone where id = #{id}")
    int findUserIdById(@Param("id") int id) throws Exception;

    /**
     * 单独查询
     * @param id    id
     * @return  {@link ZoneResponse}
     * @throws Exception
     */
    @Select("select " +
            "A.id,A.userId,B.emailName email,A.msg," +
            "A.createDate date,A.msgType,A.praise " +
            "from user_zone A join user B on A.userId = B.id where A.id = #{id}")
    ZoneResponse findZoneById(@Param("id") int id) throws Exception;

    /**
     * 写说说
     *
     * @param userZone {@link UserZone}
     * @throws Exception
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user_zone(userId,msg,msgType)values(#{userId},#{msg},#{msgType})")
    void insertZone(UserZone userZone) throws Exception;

    /**
     * @param count  总数
     * @param zoneId id
     * @throws Exception
     */
    @Update("update user_zone set praise = #{count} where id = #{zoneId}")
    void updatePraise(int count, int zoneId) throws Exception;

    /**
     * 删除
     *
     * @param id id
     * @throws Exception
     */
    @Delete("delete from user_zone where id = #{id}")
    void deleteZone(@Param("id") int id) throws Exception;

    /**
     * 删除单个空间所有图片
     *
     * @param zoneId 空间id
     * @throws Exception
     */
    @Delete("delete from zone_img where zoneId = #{zoneId}")
    void deleteAllZoneImg(@Param("zoneId") int zoneId) throws Exception;

    /**
     * 删除单个图片
     *
     * @param id    id
     * @throws Exception
     */
    @Delete("delete from zone_img where id = #{id}")
    void deleteZoneImgById(@Param("id") Integer id) throws Exception;

    /**
     * 查询单个空间所有图片
     *
     * @param zoneId 空间id
     * @return {@link ZoneImg}
     * @throws Exception
     */
    @Select("select id,zoneId,imgUrl,width,height,dominantColor from zone_img where zoneId = #{zoneId}")
    List<ZoneImgResponse> findAllZoneImgByZoneId(@Param("zoneId") int zoneId) throws Exception;

    /**
     * 添加图片
     *
     * @param zoneImg   {@link ZoneImg}
     * @throws Exception
     */
    @Insert("insert into zone_img(zoneId,imgUrl,width,height,dominantColor) values (#{zoneId},#{imgUrl},#{width},#{height},#{dominantColor})")
    void insertZoneImg(ZoneImg zoneImg) throws Exception;

    /**
     * 查询所有
     *
     * @param currIndex 起始
     * @param pageSize  多少
     * @param mode      模式
     * @return {@link ZoneResponse}
     * @throws Exception
     */
    @SelectProvider(type = SqlProvider.class, method = "selectAllZonePersonSql")
    List<ZoneResponse> findAllZone(Integer currIndex, int pageSize, Integer mode) throws Exception;

    /**
     * 查询用户所有
     *
     * @param currIndex 起始
     * @param pageSize  多少
     * @param userId    用户id
     * @param mode      模式
     * @return {@link ZoneResponse}
     * @throws Exception
     */
    @SelectProvider(type = SqlProvider.class, method = "selectUserZonePersonSql")
    List<ZoneResponse> findZoneByUser(Integer currIndex, Integer pageSize, Integer userId, Integer mode) throws Exception;

    class SqlProvider {
        public String selectAllZonePersonSql(Integer mode) {
            return new SQL() {{
                SELECT("A.id", "A.userId", "B.emailName email", "A.msg", "A.createDate date", "A.msgType");
                FROM("user_zone A").JOIN("user B on A.userId = B.id");
                if (mode == 0)
                    ORDER_BY("A.createDate DESC,A.praise DESC limit #{currIndex},#{pageSize}");
                else if (mode == 1)
                    ORDER_BY("A.createDate DESC limit #{currIndex},#{pageSize}");
                else if (mode == 2)
                    ORDER_BY("A.praise DESC limit #{currIndex},#{pageSize}");
            }}.toString();
        }

        public String selectUserZonePersonSql(Integer mode) {
            return new SQL() {{
                SELECT("A.id", "A.userId", "B.emailName email", "A.msg", "A.createDate date", "A.msgType").
                        FROM("user_zone A").JOIN("user B on A.userId = B.id").WHERE("A.userId = #{userId}");
                if (mode == 0)
                    ORDER_BY("A.createDate DESC,praise DESC limit #{currIndex},#{pageSize}");
                else if (mode == 1)
                    ORDER_BY("A.createDate DESC limit #{currIndex},#{pageSize}");
                else if (mode == 2)
                    ORDER_BY("A.praise DESC limit #{currIndex},#{pageSize}");
            }}.toString();
        }

    }
}
