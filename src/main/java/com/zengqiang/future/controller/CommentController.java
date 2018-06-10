package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.CommentForm;
import com.zengqiang.future.service.ICommentService;
import com.zengqiang.future.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comment")
@ResponseBody
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @RequestMapping("")
    public ServerResponse comment(@RequestBody CommentForm form, HttpServletRequest request){
        int userId= TokenUtil.getUserId(request.getHeader("Authorization"));
        if(form!=null){
            form.setUserId(userId);
        }
        return commentService.comment(form);
    }

}
