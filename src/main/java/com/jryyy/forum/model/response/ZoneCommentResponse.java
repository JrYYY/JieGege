package com.jryyy.forum.model.response;

import com.jryyy.forum.model.IdentifiableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 空间评论
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ZoneCommentResponse extends IdentifiableEntity {

    /** 邮箱 */
    private String email;

    /** 评论 */
    private String comment;

    /** 时间 */
    private Date date;
}
