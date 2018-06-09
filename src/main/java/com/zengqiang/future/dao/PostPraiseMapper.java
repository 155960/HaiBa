package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.PostPraise;

public interface PostPraiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostPraise record);

    int insertSelective(PostPraise record);

    PostPraise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostPraise record);

    int updateByPrimaryKey(PostPraise record);
}