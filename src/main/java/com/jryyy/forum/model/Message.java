package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 信息数据模型
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * 来自于
     */
    @NotNull(message = "发送地址不能为空")
    private Integer from;

    /**
     * 发放到
     */
    @NotNull(message = "接收地址不能为空")
    private Integer to;

    /**
     * 信息主体
     */
    @NotNull(message = "消息主体不能为空")
    private String message;

    /**
     * 信息类型
     */
    @NotNull(message = "消息类型不能为空")
    private String type;

    private LocalDateTime date = LocalDateTime.now();
}


