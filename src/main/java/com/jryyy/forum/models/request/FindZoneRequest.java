package com.jryyy.forum.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindZoneRequest {
    /* 每页大小 */
    @NotBlank(message = "pageSize不能为空")
    private Integer pageSize;

    /* 当前页 */
    @NotBlank(message = "offset不能为空")
    private Integer curPageNumber;
}
