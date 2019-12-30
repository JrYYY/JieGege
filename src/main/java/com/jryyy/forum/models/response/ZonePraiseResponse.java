package com.jryyy.forum.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonePraiseResponse {

    int userId;

    String username;

    String email;
}
