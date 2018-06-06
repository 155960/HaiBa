package com.zengqiang.future.pojo;

import java.util.Date;

public class Post {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer addrId;

    private String content;

    private Integer numPraise;

    private Integer numComment;

    private Integer isEnabledComment;

    private Date createTime;

    private Date updateTime;

    public Post(Integer id, Integer userId, Integer type, Integer addrId, String content, Integer numPraise, Integer numComment, Integer isEnabledComment, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.addrId = addrId;
        this.content = content;
        this.numPraise = numPraise;
        this.numComment = numComment;
        this.isEnabledComment = isEnabledComment;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Post() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getNumPraise() {
        return numPraise;
    }

    public void setNumPraise(Integer numPraise) {
        this.numPraise = numPraise;
    }

    public Integer getNumComment() {
        return numComment;
    }

    public void setNumComment(Integer numComment) {
        this.numComment = numComment;
    }

    public Integer getIsEnabledComment() {
        return isEnabledComment;
    }

    public void setIsEnabledComment(Integer isEnabledComment) {
        this.isEnabledComment = isEnabledComment;
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