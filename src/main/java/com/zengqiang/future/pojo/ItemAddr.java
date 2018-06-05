package com.zengqiang.future.pojo;

public class ItemAddr {
    private Integer id;

    private Integer type;

    private Integer itemId;

    private Integer addrId;

    public ItemAddr(Integer id, Integer type, Integer itemId, Integer addrId) {
        this.id = id;
        this.type = type;
        this.itemId = itemId;
        this.addrId = addrId;
    }

    public ItemAddr() {
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }
}