package com.zengqiang.future.info;

import java.util.Date;

public class PostInfo {

    private Integer id;

    private Integer type;

    private Integer numPraise;

    private Integer numComment;

    private String addr;

    private Integer isEnabledComment;

    private String content;

    private Date updateTime;

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
