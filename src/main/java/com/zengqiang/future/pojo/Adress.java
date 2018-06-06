package com.zengqiang.future.pojo;

public class Adress {
    private Integer id;

    private String name;

    private String addrDetail;

    public Adress(Integer id, String name, String addrDetail) {
        this.id = id;
        this.name = name;
        this.addrDetail = addrDetail;
    }

    public Adress() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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