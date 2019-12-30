package com.jryyy.forum.models.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesResponse {
    private Integer id;

    private Integer zoneId;

    private String name;

    private Timestamp createDate;

    private String img;

    private String msg;
}
