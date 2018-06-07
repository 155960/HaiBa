package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.ItemMapper;
import com.zengqiang.future.dao.PostMapper;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.form.PostItemForm;
import com.zengqiang.future.pojo.Item;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Transactional
    public ServerResponse createPostItem(PostItemForm form){

        try{
            Post post=postFormToPost(form);
            int rowCount=postMapper.insert(post);
            List<Item> items=postFormToItem(form,post.getId());
            rowCount=itemMapper.insetItems(items);
            return ServerResponse.createBySuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("发布失败");
        }

    }

    private List<Item> postFormToItem(PostItemForm form,int postId){
        List<ItemForm> itemForms=form.getItems();
        List<Item> items=new ArrayList<>();
        if(itemForms!=null&&itemForms.size()>0){
            for(int i=0;i<itemForms.size();i++){
                Item item=new Item();
                ItemForm itemForm=itemForms.get(i);
                item.setItemDescribe(itemForm.getItemDescribe());
                item.setNumber(itemForm.getNumber());
                item.setPostId(postId);
                item.setPrice(itemForm.getPrice());
                item.setTitle(itemForm.getTitle());
            }
        }
        return items;
    }

    private Post postFormToPost(PostItemForm form){
        Post post=new Post();
        post.setIsEnabledComment(form.getIsEnabledComment());
        post.setType(post.getType());
        post.setUserId(form.getUserId());
        post.setAddrId(form.getAddrId());
        post.setContent(form.getContent());
        return post;
    }
}
