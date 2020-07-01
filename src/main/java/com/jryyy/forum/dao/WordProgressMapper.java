package com.jryyy.forum.dao;

import com.jryyy.forum.model.WordProgress;
import org.apache.ibatis.annotations.*;

/**
 * 单词记忆进度
 *
 * @author OU
 */
@Mapper
public interface WordProgressMapper {

    /**
     * @param userId
     * @throws Exception
     */
    @Insert("insert word_progress(userId,dailyDuty,currentDailyPosition,total,modifyDate)values(#{userId},25,0,3653,now())")
    void insertProgress(Integer userId) throws Exception;

    /**
     * @param userId
     * @param currentDailyPosition
     * @throws Exception
     */
    @Update("update word_progress set currentDailyPosition = #{currentDailyPosition},modifyDate = now() where userId = #{userId}")
    void updateCurrent(Integer userId, Integer currentDailyPosition) throws Exception;

    /**
     * 修改任务量
     *
     * @param userId    用户id
     * @param dailyDuty
     * @throws Exception
     */
    @Update("update word_progress set dailyDuty = #{dailyDuty} where userId = #{userId}")
    int updateDailyDuty(Integer userId, Integer dailyDuty) throws Exception;

    /**
     * @param userId
     * @throws Exception
     */
    @Delete("delete from word_progress where userId = userId")
    void deleteProgress(int userId) throws Exception;

    /**
     * @param userId
     * @return
     * @throws Exception
     */
    @Select("select userId,dailyDuty,currentDailyPosition,total,modifyDate from word_progress where userId = #{userId}")
    WordProgress findWordProgressByUserId(Integer userId) throws Exception;


}
