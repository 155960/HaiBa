package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.PostForm;
import com.zengqiang.future.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
@ResponseBody
public class PostController {

    @Autowired
    private IPostService postService;

    @RequestMapping("/create")
    public ServerResponse createPost(PostForm postForm){

        return null;
    }

}
