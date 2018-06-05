package com.zengqiang.future.pojo;

import java.util.Date;

public class Account {
    private Integer id;

    private Integer type;

    private String account;

    private String password;

    private Date createTime;

    public Account(Integer id, Integer type, String account, String password, Date createTime) {
        this.id = id;
        this.type = type;
        this.account = account;
        this.password = password;
        this.createTime = createTime;
    }

    public Account() {
        super();
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}