package com.jryyy.forum.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 所有响应数据的基类
 *
 * @author JrYYY
 */
@ToString
@Getter
@NoArgsConstructor
public class Response<T> {
    /** 状态码 */
    private Integer status = 200;

    /**数据 */
    private T data;

    /** 信息 */
    private String message = "操作成功";

    public Response(T data) {
        this.data = data;
    }

}
