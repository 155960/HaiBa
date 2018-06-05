package com.zengqiang.future.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
    private Integer id;

    private String title;

    private String itemDescribe;

    private BigDecimal price;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    public Item(Integer id, String title, String itemDescribe, BigDecimal price, Integer number, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.itemDescribe = itemDescribe;
        this.price = price;
        this.number = number;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Item() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getItemDescribe() {
        return itemDescribe;
    }

    public void setItemDescribe(String itemDescribe) {
        this.itemDescribe = itemDescribe == null ? null : itemDescribe.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}