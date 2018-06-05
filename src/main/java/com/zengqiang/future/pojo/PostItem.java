package com.zengqiang.future.pojo;

public class PostItem {
    private Integer id;

    private Integer postId;

    private Integer itemId;

    public PostItem(Integer id, Integer postId, Integer itemId) {
        this.id = id;
        this.postId = postId;
        this.itemId = itemId;
    }

    public PostItem() {
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}