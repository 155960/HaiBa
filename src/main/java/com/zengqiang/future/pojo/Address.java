package com.zengqiang.future.pojo;

public class Address {
    private Integer id;

    private Integer userId;

    private String name;

    private String addrDetail;

    public Address(Integer id, Integer userId, String name, String addrDetail) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.addrDetail = addrDetail;
    }

    public Address() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail == null ? null : addrDetail.trim();
    }
}