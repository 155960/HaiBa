package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.ItemAddr;

public interface ItemAddrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemAddr record);

    int insertSelective(ItemAddr record);

    ItemAddr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemAddr record);

    int updateByPrimaryKey(ItemAddr record);
}