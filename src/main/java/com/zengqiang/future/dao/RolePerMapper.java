package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.RolePer;

public interface RolePerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePer record);

    int insertSelective(RolePer record);

    RolePer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePer record);

    int updateByPrimaryKey(RolePer record);
}