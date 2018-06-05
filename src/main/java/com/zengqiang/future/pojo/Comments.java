package com.zengqiang.future.pojo;

import java.util.Date;

public class Comments {
    private Integer id;

    private String content;

    private Integer postId;

    private Integer userId;

    private Integer numReply;

    private Date commentTime;

    public Comments(Integer id, String content, Integer postId, Integer userId, Integer numReply, Date commentTime) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.numReply = numReply;
        this.commentTime = commentTime;
    }

    public Comments() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNumReply() {
        return numReply;
    }

    public void setNumReply(Integer numReply) {
        this.numReply = numReply;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}