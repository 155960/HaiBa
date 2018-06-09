package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.Const;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.GoodMapper;
import com.zengqiang.future.dao.ItemMapper;
import com.zengqiang.future.dao.PostMapper;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.form.PostItemForm;
import com.zengqiang.future.info.GoodAndItemInfo;
import com.zengqiang.future.info.GoodInfo;
import com.zengqiang.future.info.ItemInfo;
import com.zengqiang.future.pojo.Good;
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

    @Autowired
    private GoodMapper goodMapper;

    public ServerResponse detail(int postId,int type){
        try{
            if(type== Const.Post.GOOD){
                List<Good> goods;
                goods=goodMapper.selectGoodsByPostIdAndType(postId,Const.Post.GOOD);
                List<GoodInfo> infos=goodsToInfos(goods);
                return ServerResponse.createBySuccess(infos);
            }else if(type==Const.Post.ITEM){
                List<Item> items=new ArrayList<>();
                items=itemMapper.selectItemsByPostId(postId);
                List<ItemInfo> itemInfos=findItemInfo(items);
                return ServerResponse.createBySuccess(itemInfos);
            }else{
                List<Good> goods=goodMapper.selectGoodsByPostIdAndType(postId,Const.Post.GOOD);
                List<GoodInfo> infos=goodsToInfos(goods);
                List<Item> items=itemMapper.selectItemsByPostId(postId);
                List<ItemInfo> itemInfos=findItemInfo(items);
                GoodAndItemInfo info=new GoodAndItemInfo();
                info.setGoodInfos(infos);
                info.setItemInfos(itemInfos);
                return ServerResponse.createBySuccess(info);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("查询出错");
        }
    }

    private List<GoodInfo> goodsToInfos(List<Good> goods){
        List<GoodInfo> infos=new ArrayList<>();
        for(int i=0;i<goods.size();i++){
            GoodInfo info=goodToInfo(goods.get(i));
            infos.add(info);
        }
        return infos;
    }

    private GoodInfo goodToInfo(Good good){
        GoodInfo info=new GoodInfo();
        info.setId(good.getId());
        info.setUrl(good.getUrl());
        return info;
    }


    private List<ItemInfo> findItemInfo(List<Item> items){
        List<ItemInfo> itemInfos=new ArrayList<>();
        for(int i=0;i<items.size();i++){
            ItemInfo itemInfo=itemToItemInfo(items.get(i));
            itemInfos.add(itemInfo);
        }
        return itemInfos;
    }

    private ItemInfo itemToItemInfo(Item item){
        ItemInfo info=new ItemInfo();
        info.setId(item.getId());
        info.setItemDescribe(item.getItemDescribe());
        info.setNumber(item.getNumber());
        info.setPrice(item.getPrice().toString());
        info.setTitle(item.getTitle());
        info.setUpdateTime(info.getUpdateTime());
        List<Good> goods=goodMapper.selectGoodsByPostIdAndType(item.getId(),Const.Post.ITEM);
        List<GoodInfo> goodInfos=goodsToInfos(goods);
        info.setInfos(goodInfos);
        return info;
    }

    @Transactional
    public ServerResponse delete(int postId){
        try{
            itemMapper.deleteByPostId(postId);
            int rowCount=postMapper.deleteByPrimaryKey(postId);
            if(rowCount>0){
                return ServerResponse.createBySuccessMessage("删除成功");
            }else{
                return ServerResponse.createBySuccessMessage("该帖子不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("未知错误，删除失败");
        }
    }

    @Transactional
    public ServerResponse update(PostItemForm form){

        try{
            Post post=postFormToPost(form);
            int rowCount=postMapper.updateByPrimaryKeySelective(post);
            List<Item> items=postFormToItem(form,post.getId());
            rowCount=itemMapper.updateItems(items);
            return ServerResponse.createBySuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }

    }

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
                item.setId(itemForm.getId());
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
        post.setId(form.getId());
        return post;
    }

    /**
     *
     * @param f true 点赞 false 取消
     * @return
     */
    public ServerResponse praise(int postId,boolean f){
        final String p=postId+"praise";
        try{
            synchronized (p){
                int praiseNum=postMapper.selectPraiseById(postId);
                if(f){
                    praiseNum+=1;

                }else{
                    praiseNum-=1;
                }
                Post post=new Post();
                post.setId(postId);
                post.setNumPraise(praiseNum);
                postMapper.updateByPrimaryKeySelective(post);
                return ServerResponse.createBySuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }

    }
}
