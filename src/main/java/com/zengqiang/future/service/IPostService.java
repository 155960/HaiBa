package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.PostItemForm;

public interface IPostService {
    ServerResponse createPostItem(PostItemForm form);
}
