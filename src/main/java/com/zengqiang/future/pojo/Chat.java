package com.zengqiang.future.pojo;

import java.util.Date;

public class Chat {
    private Integer id;

    private String content;

    private Integer sendStatus;

    private Integer recStatus;

    private Integer type;

    private Integer recUserId;

    private Integer sendUserId;

    private Date sendTime;

    public Chat(Integer id, String content, Integer sendStatus, Integer recStatus, Integer type, Integer recUserId, Integer sendUserId, Date sendTime) {
        this.id = id;
        this.content = content;
        this.sendStatus = sendStatus;
        this.recStatus = recStatus;
        this.type = type;
        this.recUserId = recUserId;
        this.sendUserId = sendUserId;
        this.sendTime = sendTime;
    }

    public Chat() {
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

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRecUserId() {
        return recUserId;
    }

    public void setRecUserId(Integer recUserId) {
        this.recUserId = recUserId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}