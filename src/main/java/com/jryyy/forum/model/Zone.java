package com.jryyy.forum.model;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 用户空间类
  * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Zone extends IdentifiableEntity{

    /** 用户id */
     private int userId;

     /** 内容 */
     private String msg;

     /** 创建时间 */
     private LocalDateTime createDate;

     /** 类型 */
     private int msgType;

     /** 赞同数 */
     private Integer favorite;

}
