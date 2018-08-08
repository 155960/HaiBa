package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.Const;
import com.zengqiang.future.dao.GoodMapper;
import com.zengqiang.future.pojo.Good;
import com.zengqiang.future.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl implements IGoodService {

    @Autowired
    private GoodMapper mapper;

    public Good createGood(String name,String displayName,String account,int postId){
        Good good=new Good();
        good.setName(name);
        good.setDisplayName(displayName);
        good.setPostId(postId);
        good.setUrl(Const.HTTP.PREFIX+account+"/"+name);
        mapper.insert(good);
        return good;
    }
}
