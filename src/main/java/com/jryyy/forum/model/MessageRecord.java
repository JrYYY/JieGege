package com.jryyy.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;

import java.time.LocalDateTime;


/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRecord {

    /**
     * 来自于
     */
    private Integer fromId;

    /**
     * 发放到
     */
    private Integer toId;

    /**
     * 信息主体
     */
    private String message;

    /**
     * 信息类型
     */
    private String type;


    private LocalDateTime date;
}
