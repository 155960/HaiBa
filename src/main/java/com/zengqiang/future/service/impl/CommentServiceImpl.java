package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.CommentsMapper;
import com.zengqiang.future.dao.PostMapper;
import com.zengqiang.future.form.CommentForm;
import com.zengqiang.future.pojo.Comments;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private PostMapper postMapper;

    //考虑多线程问题,隔离级别设为重复读
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ServerResponse comment(CommentForm form){
        if(form!=null){
            //使用字符串常量池达到同步效果
            final String postId=form.getPostId()+""+"comment";
            synchronized (postId){
                Comments comments=commentFormToComment(form);
                commentsMapper.insert(comments);
                int num=postMapper.selectCommentById(form.getPostId());
                num++;
                Post post=new Post();
                post.setNumComment(num);
                post.setId(form.getPostId());
                int rowCount=postMapper.updateByPrimaryKeySelective(post);
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("评论失败");

    }

    private Comments commentFormToComment(CommentForm form){
        Comments comments=new Comments();
        comments.setPostId(form.getPostId());
        comments.setContent(form.getContent());
        comments.setUserId(form.getUserId());
        return comments;
    }
}
