package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    String selectPasswordByAccount(@Param("account") String account);

    int selectIdByAccount(String account);
}