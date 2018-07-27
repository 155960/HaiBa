package com.zengqiang.future.dao;

import com.zengqiang.future.pojo.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> selectPostsByUserId(Integer userId);

    int selectCommentById(Integer id);

    int selectPraiseById(Integer postId);

    List<Post> selectHotPosts(Integer addrId);

    List<Post> selectNewestPosts(@Param("addrId") Integer addrId,
                                 @Param("id") Integer id,
                                 @Param("begind") Integer begind,
                                 @Param("size") Integer size);

}