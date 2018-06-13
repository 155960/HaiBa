package com.zengqiang.future.form;

import java.util.List;

public class PostGoodForm {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer addrId;

    private Integer isEnabledComment;

    private String content;

    private List<GoodForm> goods;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public Integer getIsEnabledComment() {
        return isEnabledComment;
    }

    public void setIsEnabledComment(Integer isEnabledComment) {
        this.isEnabledComment = isEnabledComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<GoodForm> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodForm> goods) {
        this.goods = goods;
    }
}
