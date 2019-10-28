package com.jryyy.forum.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetZoneCommentRequest {

    @NotBlank
    private Integer id;

    @NotBlank
    private Integer currIndex;

    @NotBlank
    private Integer pageSize;
}
