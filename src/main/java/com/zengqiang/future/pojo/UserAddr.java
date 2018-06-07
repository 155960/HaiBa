package com.zengqiang.future.pojo;

public class UserAddr {
    private Integer id;

    private Integer type;

    private Integer userId;

    private Integer addrId;

    public UserAddr(Integer id, Integer type, Integer userId, Integer addrId) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.addrId = addrId;
    }

    public UserAddr() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }
}