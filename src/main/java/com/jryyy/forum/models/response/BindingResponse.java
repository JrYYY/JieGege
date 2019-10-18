package com.jryyy.forum.models.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
