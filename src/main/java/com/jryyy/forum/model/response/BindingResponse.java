package com.jryyy.forum.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author OU
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindingResponse {

    private Integer id;

    private String username;

    private String avatar;

    private Date date;
}
