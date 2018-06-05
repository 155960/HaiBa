package com.zengqiang.future.pojo;

import java.util.Date;

public class Image {
    private Integer id;

    private Integer postId;

    private Integer type;

    private Integer orderNum;

    private String url;

    private Date uploadTime;

    private Date overdueTime;

    public Image(Integer id, Integer postId, Integer type, Integer orderNum, String url, Date uploadTime, Date overdueTime) {
        this.id = id;
        this.postId = postId;
        this.type = type;
        this.orderNum = orderNum;
        this.url = url;
        this.uploadTime = uploadTime;
        this.overdueTime = overdueTime;
    }

    public Image() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }
}