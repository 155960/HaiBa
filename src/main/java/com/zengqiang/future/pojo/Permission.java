package com.zengqiang.future.pojo;

public class Permission {
    private Integer id;

    private Integer code;

    public Permission(Integer id, Integer code) {
        this.id = id;
        this.code = code;
    }

    public Permission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}