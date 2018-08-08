package com.zengqiang.future.service;

import com.zengqiang.future.pojo.Good;

public interface IGoodService {
    Good createGood(String name, String displayName, String account, int postId);
}
