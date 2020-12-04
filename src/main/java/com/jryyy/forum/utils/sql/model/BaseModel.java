package com.jryyy.forum.utils.sql.model;

public class BaseModel<T> {
    private T data;
    private PagingInfo pagingInfo;

    public BaseModel(T data) {
        this.data = data;
    }

    public BaseModel(T data, PagingInfo pagingInfo) {
        this.data = data;
        this.pagingInfo = pagingInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public Class getDateClass() {
        return data.getClass();
    }

}

