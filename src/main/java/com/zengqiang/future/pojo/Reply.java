package com.zengqiang.future.pojo;

import java.util.Date;

public class Reply {
    private Integer id;

    private Integer rootCommentId;

    private Integer commentId;

    private Integer replyType;

    private String content;

    private Integer userId;

    private Integer replyUserId;

    private Date replyTime;

    public Reply(Integer id, Integer rootCommentId, Integer commentId, Integer replyType, String content, Integer userId, Integer replyUserId, Date replyTime) {
        this.id = id;
        this.rootCommentId = rootCommentId;
        this.commentId = commentId;
        this.replyType = replyType;
        this.content = content;
        this.userId = userId;
        this.replyUserId = replyUserId;
        this.replyTime = replyTime;
    }

    public Reply() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRootCommentId() {
        return rootCommentId;
    }

    public void setRootCommentId(Integer rootCommentId) {
        this.rootCommentId = rootCommentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}