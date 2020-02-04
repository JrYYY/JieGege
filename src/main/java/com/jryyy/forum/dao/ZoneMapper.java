package com.jryyy.forum.dao;

import com.jryyy.forum.model.Zone;
import com.jryyy.forum.model.ZoneImg;
import com.jryyy.forum.model.request.GetZoneRequest;
import com.jryyy.forum.model.response.ZoneResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 用户空间dao
 * @author OU
 */
@Mapper
public interface ZoneMapper {

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
    @Select("select id,userId,msg,createDate date,msgType,praise from user_zone where id = #{id}")
    Zone findZoneById(@Param("id") int id) throws Exception;

    /**
     * 写说说
     *
     * @param zone {@link Zone}
     * @throws Exception
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user_zone(userId,msg,msgType)values(#{userId},#{msg},#{msgType})")
    void insertZone(Zone zone) throws Exception;

    /**
     * @param count  总数
     * @param zoneId id
     * @throws Exception
     */
    @Update("update user_zone set praise = #{count} where id = #{zoneId}")
    void updatePraise(int count, int zoneId) throws Exception;

    /**
     * 删除
     * @param userId    用户id
     * @param id id
     * @return 删除行数
     * @throws Exception
     */
    @Delete("delete from user_zone where id = #{id} and userId = #{userId}")
    int deleteZone(@Param("userId") Integer userId,@Param("id") int id) throws Exception;

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
     */
    @Select("select id,zoneId,imgUrl,width,height,dominantColor from zone_img where zoneId = #{zoneId}")
    List<ZoneImg> findAllZoneImgByZoneId(@Param("zoneId") int zoneId);

    /**
     * 添加图片
     *
     * @param zoneImg   {@link ZoneImg}
     * @throws Exception
     */
    @Insert("insert into zone_img(zoneId,imgUrl,width,height,dominantColor) values (#{zoneId},#{imgUrl},#{width},#{height},#{dominantColor})")
    void insertZoneImg(ZoneImg zoneImg);


    /**
     * 查询用户所有
     *
     * @param request {@link GetZoneRequest}
     * @return {@link Zone}
     * @throws Exception
     */
    @SelectProvider(type = SqlProvider.class, method = "selectUserZoneSql")
    List<Zone> findZone(GetZoneRequest request) throws Exception;

    class SqlProvider {
        public String selectUserZoneSql(GetZoneRequest request) {
            return new SQL() {{
                SELECT("id", "userId", "msg", "createDate", "msgType","praise favorite").
                        FROM("user_zone");
                if(request.getId() != null) { WHERE("userId = #{id}"); }
                if (request.getMode() == 1) {
                    ORDER_BY("createDate DESC limit #{curPage},#{pageSize}"); }
                if (request.getMode() == 2) {
                    ORDER_BY("praise DESC limit #{curPage},#{pageSize}"); }
            }}.toString();
        }
    }
}
