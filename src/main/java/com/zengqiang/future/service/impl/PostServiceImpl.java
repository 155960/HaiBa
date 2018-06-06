package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.PostMapper;
import com.zengqiang.future.form.PostForm;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostMapper postMapper;

    public ServerResponse createPost(PostForm form){

    }

    private Post postFormToPost(PostForm form){
        Post post=new Post();
        post.setIsEnabledComment(form.getIsEnabledComment());
        post.setItemId(form.getItemId());
        post.setType(post.getType());
        post.setUserId(form.getUserId());
    }
}
