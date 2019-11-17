package com.jryyy.forum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorites {
    private Integer id;

    private Integer userId;

    private Integer zoneId;
}
