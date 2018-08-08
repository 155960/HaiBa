package com.zengqiang.future.pojo;

import java.io.Serializable;
import java.util.Date;

public class SystemMessage implements Serializable {

    private String message;

    private String createTime;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
