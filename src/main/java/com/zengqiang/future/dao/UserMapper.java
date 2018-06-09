package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByAccountId(Integer id);

    int updateByAccountId(User user);

    Integer selectIdByAccountId(Integer accountId);
}