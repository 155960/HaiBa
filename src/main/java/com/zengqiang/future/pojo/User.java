package com.zengqiang.future.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private Integer accountId;

    private String nickName;

    private Integer status;

    private Integer messages;

    private Integer imageId;

    private String personalIndroduce;

    private String qqNumber;

    private String email;

    private String phone;

    private Date createTime;

    private Date updateTime;

    public User(Integer id, Integer accountId, String nickName, Integer status, Integer messages, Integer imageId, String personalIndroduce, String qqNumber, String email, String phone, Date createTime, Date updateTime) {
        this.id = id;
        this.accountId = accountId;
        this.nickName = nickName;
        this.status = status;
        this.messages = messages;
        this.imageId = imageId;
        this.personalIndroduce = personalIndroduce;
        this.qqNumber = qqNumber;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getPersonalIndroduce() {
        return personalIndroduce;
    }

    public void setPersonalIndroduce(String personalIndroduce) {
        this.personalIndroduce = personalIndroduce == null ? null : personalIndroduce.trim();
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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