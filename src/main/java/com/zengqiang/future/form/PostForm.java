package com.zengqiang.future.form;

public class PostForm {
    private Integer userId;

    private Integer type;

    private Integer itemId;

    private Integer isEnabledComment;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getIsEnabledComment() {
        return isEnabledComment;
    }

    public void setIsEnabledComment(Integer isEnabledComment) {
        this.isEnabledComment = isEnabledComment;
    }
}
