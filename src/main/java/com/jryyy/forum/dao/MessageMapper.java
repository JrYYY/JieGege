package com.jryyy.forum.dao;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.MessageRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OU
 */
@Mapper
public interface MessageMapper {

    /**
     * 写入信息
     * @param message
     * @throws Exception
     */
    @Insert("insert into message_record(fromId,toId,message,type,date)values(#{from},#{to},#{message},#{type},now())")
    void insertMessage(Message message) throws Exception;

    /**
     * 清除消息记录
     * @param from    发id
     * @param to      到id
     * @param dateTime  时间
     * @return  清除数目
     * @throws Exception
     */
    @Delete("delete from message_record where fromId = #{from} and toId = #{to} and date < #{dataTime}")
    int deleteMessage(Integer from, Integer to, LocalDateTime dateTime)throws Exception;

    /**
     * 查看消息记录
     * @param from  发
     * @param to    收
     * @return  {@link Message}
     * @throws Exception
     */
    @Select("select fromId,toId,message,type,date from Message_record " +
            "where fromId = #{from} and toId = #{to}")
    @Results({@Result(property = "date", column = "date", jdbcType = JdbcType.TIMESTAMP)})
    List<MessageRecord> findMessageByFromIdAndToId(Integer from,Integer to)throws Exception;

    /**
     * 更改阅读状态
     * @param from  发
     * @param to    收
     * @throws Exception
     */
    @Update("update message_record set status = 1 where fromId = #{from} and toId = #{to}")
    void updateMessageStatus(Integer from,Integer to)throws Exception;

    /**
     * 获取最新消息
     * @param from  发
     * @param to    收
     * @return  {@link Message}
     */
    @Select("select fromId,toId,message,type,date from message_record " +
            "where fromId = #{from} and toId = #{to} order by date limit 1")
    @Results({@Result(property = "date", column = "date", jdbcType = JdbcType.DATETIMEOFFSET)})
    MessageRecord findMessageByDate(Integer from, Integer to);

    /**
     *  未查看用户信息列表
     * @param to 到
     * @return  未查看用户信息列表
     * @throws Exception
     */
    @Select("select fromId from message_record where toId = #{to} and status = 0 group by fromId")
    List<Integer> findFromByTo(Integer to)throws Exception;

    /**
     * 未查看消息数量
     * @param from  发
     * @param to    收
     * @return  数量
     * @throws Exception
     */
    @Select("select count(*) from message_record where fromId = #{from} and toId = #{to} and status = 0")
    Integer findNumberByFromIdAndToId(Integer from,Integer to);


}
