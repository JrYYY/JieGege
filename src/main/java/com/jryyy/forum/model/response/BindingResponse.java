package com.jryyy.forum.model.response;


import lombok.*;

import java.sql.Date;

/**
 * @author OU
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindingResponse {
    private Integer id;

    UserInfoResponse userInfo;

    private Date date;
    private Integer userId;
}
