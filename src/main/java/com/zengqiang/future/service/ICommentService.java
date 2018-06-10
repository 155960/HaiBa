package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.CommentForm;

public interface ICommentService {

    ServerResponse comment(CommentForm form);
}
