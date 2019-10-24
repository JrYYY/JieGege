package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneCommentResponse {
    private String email;

    private String msg;

    private Date date;
}
