package com.jryyy.forum.model.response;

import com.jryyy.forum.model.IdentifiableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 空间评论
 *
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ZoneCommentResponse extends IdentifiableEntity {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户信息
     */
    private UserInfoResponse userInfo;

    /**
     * 评论
     */
    private String comment;


    /**
     * 时间
     */
    private LocalDateTime createDate;

}
