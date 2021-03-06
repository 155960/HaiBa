package com.zengqiang.future.service;

import com.alibaba.fastjson.JSON;
import com.zengqiang.future.dao.AccountMapper;
import com.zengqiang.future.dao.ItemMapper;
import com.zengqiang.future.dao.UserMapper;
import com.zengqiang.future.pojo.Account;
import com.zengqiang.future.pojo.Item;
import com.zengqiang.future.pojo.Post;
import com.zengqiang.future.pojo.User;
import com.zengqiang.future.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("testService")
public class TestService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    private  IPostService postService;

    public void select(int a){
        System.out.println("#########"+a);
    }

    public void update(int a){
        System.out.println("&&&&&&&&&&&"+a);
    }

    public void test(){
        Runnable runnable= () -> postService.praise(1,true);
        for(int i=0;i<120;i++){
            new Thread(runnable).start();
        }

       // itemMapper.deleteByPrimaryKey(18);
        /*List<Item> items=new ArrayList<>();
        for(int i=1;i<5;i++){
            Item item=new Item();
            item.setPrice(new BigDecimal(23.1));
            item.setPostId(2);
            item.setTitle("test");
            item.setItemDescribe("3435");
            items.add(item);
        }
        itemMapper.insetItems(items);*/
    }


    public static void main(String[] args) {

        /*User user=new User();
        user.setPhone("123456");
        user.setQqNumber("12345899");
        String s=JSON.toJSONString(user);
        Post post=new Post();
        post.setContent("woedgsdfdgf");
        post.setType(1);

        List<Post> list=new ArrayList<>();
        list.add(post);
        list.add(new Post());
        System.out.println(JSON.toJSON(list)+"\n"+s);*/
    }
}
