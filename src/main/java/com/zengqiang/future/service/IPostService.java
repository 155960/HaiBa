package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.PostGoodForm;
import com.zengqiang.future.form.PostItemForm;

public interface IPostService {
    ServerResponse createPostItem(PostItemForm form);
    ServerResponse update(PostItemForm form);
    ServerResponse delete(int postId);
    ServerResponse detail(int postId,int type);
    ServerResponse praise(int postId,boolean f);
    ServerResponse createGood(PostGoodForm form);
    ServerResponse getHotPost(int addrId);
    ServerResponse getNewestPost(int addrId,int id,int begin,int size);
}
