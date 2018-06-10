package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.PostItemForm;
import com.zengqiang.future.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/posts")
@ResponseBody
public class PostController {

    @Autowired
    private IPostService postService;

    @RequestMapping("/create_item")
    public ServerResponse createPost(@RequestBody PostItemForm itemForm){
        return postService.createPostItem(itemForm);
    }

    @RequestMapping("/update")
    public ServerResponse update(@RequestBody PostItemForm itemForm){
        return postService.update(itemForm);
    }

    @RequestMapping("/delete")
    public ServerResponse delete(int postId){
        return postService.delete(postId);
    }

    @RequestMapping("/detail")
    public ServerResponse detail(int postId,int type){
        return postService.detail(postId,type);
    }

    @RequestMapping("/praise")
    public ServerResponse praise(int postId){
        return postService.praise(postId,true);
    }

    @RequestMapping("/unpraise")
    public ServerResponse unpraise(int postId){
        return postService.praise(postId,false);
    }

}
