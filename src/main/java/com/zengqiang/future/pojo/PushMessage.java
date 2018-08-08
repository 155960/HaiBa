package com.zengqiang.future.pojo;

import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.Date;

public class PushMessage implements Serializable {

    private String message;

    private Date createTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
