package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.UserAddr;

public interface UserAddrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAddr record);

    int insertSelective(UserAddr record);

    UserAddr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAddr record);

    int updateByPrimaryKey(UserAddr record);
}