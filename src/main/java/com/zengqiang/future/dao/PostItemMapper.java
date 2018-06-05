package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.PostItem;

public interface PostItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostItem record);

    int insertSelective(PostItem record);

    PostItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostItem record);

    int updateByPrimaryKey(PostItem record);
}