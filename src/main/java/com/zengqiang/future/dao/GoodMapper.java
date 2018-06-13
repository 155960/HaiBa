package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    int insertGoods(List<Good> goods);

    Good selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);

    List<Good> selectGoodsByPostIdAndType(@Param("postId") Integer postId, @Param("type") Integer type);
}