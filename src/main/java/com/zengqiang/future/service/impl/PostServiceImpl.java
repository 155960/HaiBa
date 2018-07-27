package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.Const;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.GoodMapper;
import com.zengqiang.future.dao.ItemMapper;
import com.zengqiang.future.dao.PostMapper;
import com.zengqiang.future.form.GoodForm;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.form.PostGoodForm;
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
import java.util.concurrent.locks.*;

@Service
public class PostServiceImpl implements IPostService {

    private static Object object=new Object();
    private static final int DEFAULT_SIZE=20;
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    RedisCacheServiceImpl cacheService;

    /**
     * 存在上拉与下拉刷新，考虑到id越大时间越早，用id排序
     * 下拉<id 上拉 >id，id未传时代表全表范围
     * @param addrId
     * @param begin
     * @param size
     * @return
     */
    public ServerResponse getNewestPost(int addrId,int id,int begin,int size){
        if(id==0){
            id=Integer.MAX_VALUE;
        }
        if(size==0){
            size=DEFAULT_SIZE;
        }
        try{
            List<Post> posts=postMapper.selectNewestPosts(addrId,id,begin,size);
            return ServerResponse.createBySuccess(posts);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("查询出错");
        }
    }
    /**
     *
     * @param addrId
     * @return
     */
    public ServerResponse getHotPost(int addrId){
        List<Post> posts=null;
        try{
            if((posts=cacheService.getCacheList(Const.CachePrefix.HOTPOST+addrId))!=null){
            }else{
                posts=postMapper.selectHotPosts(addrId);
                cacheService.setCacheList(Const.CachePrefix.HOTPOST,posts);
            }
            return ServerResponse.createBySuccess(posts);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("查询出错");
        }


    }

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

            Post post=postFormToPost(form);
            int rowCount=postMapper.updateByPrimaryKeySelective(post);
            List<Item> items=postFormToItem(form,post.getId());
            rowCount=itemMapper.updateItems(items);
            return ServerResponse.createBySuccess();

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
                item.setPrice(itemForm.getPrice());
                item.setTitle(itemForm.getTitle());
                items.add(item);
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
        post.setNumPraise(0);
        post.setNumComment(0);
        return post;
    }

    /**
     *
     * @param f true 点赞 false 取消
     * @return
     */
   // @Transactional
    public ServerResponse praise(int postId,boolean f){
        String p=postId+"praise";
        //加入常量池
        p=p.intern();
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

    @Transactional
    public ServerResponse createGood(PostGoodForm form){
        Post post=postGoodFormToPost(form);
        int rowCount=postMapper.insert(post);
        List<Good> goods=postFormToGoods(form,post.getId());
        rowCount=goodMapper.insertGoods(goods);
        return ServerResponse.createBySuccess();
    }

    private Post postGoodFormToPost(PostGoodForm form){
        Post post=new Post();
        post.setIsEnabledComment(form.getIsEnabledComment());
        post.setType(post.getType());
        post.setUserId(form.getUserId());
        post.setAddrId(form.getAddrId());
        post.setContent(form.getContent());
        post.setId(form.getId());
        post.setNumPraise(0);
        post.setNumComment(0);
        return post;
    }

    private List<Good> postFormToGoods(PostGoodForm form,int postId){
        List<Good> goods=new ArrayList<>();
        List<GoodForm> goodForms=form.getGoods();
        for(int i=0;i<goodForms.size();i++){
            Good good=new Good();
            GoodForm goodForm=goodForms.get(i);
            good.setUrl(goodForm.getUrl());
            good.setType(goodForm.getType());
            good.setPostId(postId);
            goods.add(good);
        }
        return goods;
    }
}
