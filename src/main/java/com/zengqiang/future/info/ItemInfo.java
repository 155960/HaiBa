package com.zengqiang.future.info;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ItemInfo {
    private Integer id;
    private String title;

    private String itemDescribe;

    private String price;

    private Integer number;

    private Date updateTime;

    private List<GoodInfo> infos;

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
        this.title = title;
    }

    public String getItemDescribe() {
        return itemDescribe;
    }

    public void setItemDescribe(String itemDescribe) {
        this.itemDescribe = itemDescribe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<GoodInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<GoodInfo> infos) {
        this.infos = infos;
    }
}
