package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.PostGoodForm;
import com.zengqiang.future.form.PostItemForm;
import com.zengqiang.future.service.IPostService;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/posts")
@ResponseBody
public class PostController {

    @Autowired
    private IPostService postService;

    @RequestMapping(value = "/create_item",method = RequestMethod.POST)
    public ServerResponse createPost(@RequestBody PostItemForm itemForm){
        return postService.createPostItem(itemForm);
    }

    @RequestMapping(value = "/create_good",method = RequestMethod.POST)
    public ServerResponse createGood(@RequestBody PostGoodForm goodForm){
        try{
            return postService.createGood(goodForm);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ServerResponse update(@RequestBody PostItemForm itemForm){
        try{
            return postService.update(itemForm);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }

    }

    @RequestMapping("/delete")
    public ServerResponse delete(int postId){
        return postService.delete(postId);
    }

    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public ServerResponse detail(int postId,int type){
        return postService.detail(postId,type);
    }

    @RequestMapping(value = "/praise",method = RequestMethod.POST)
    public ServerResponse praise(int postId){
        return postService.praise(postId,true);
    }

    @RequestMapping(value = "/unpraise",method = RequestMethod.POST)
    public ServerResponse unpraise(int postId){
        return postService.praise(postId,false);
    }

    //根据地址ID查询当地热点数据
    @RequestMapping(value = "/host_post",method = RequestMethod.POST)
    public ServerResponse hotPost(int addrId){
        return postService.getHotPost(addrId);
    }

    //最新数据
    @RequestMapping(value = "/newest_post",method =RequestMethod.POST )
    public ServerResponse newestPost(int addrId,
                                     @RequestParam(value = "id",required = false)int id,
                                     int begin,
                                     @RequestParam(value = "size",required = false) int size){
        return postService.getNewestPost(addrId,id,begin,size);
    }

}
