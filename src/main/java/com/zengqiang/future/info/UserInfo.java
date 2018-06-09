package com.zengqiang.future.info;

import com.zengqiang.future.pojo.Address;

import java.util.Date;
import java.util.List;


public class UserInfo {
    private String account;
    private String nickName;
    private Integer messages;
    //头像url
    private String img;
    private String personalIndroduce;

    private String qqNumber;

    private String email;

    private String phone;

    private Date updateTime;

    //权限代码
    private int code;

    private List<AddressInfo> addresses;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AddressInfo> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressInfo> addresses) {
        this.addresses = addresses;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPersonalIndroduce() {
        return personalIndroduce;
    }

    public void setPersonalIndroduce(String personalIndroduce) {
        this.personalIndroduce = personalIndroduce;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return updateTime;
    }

    public void setCreateTime(Date createTime) {
        this.updateTime = createTime;
    }
}
