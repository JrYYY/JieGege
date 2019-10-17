package com.jryyy.forum.models.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BindingResponse {

    private Integer id;

    private String username;

    private String avatarUrl;

    private String date;
}
