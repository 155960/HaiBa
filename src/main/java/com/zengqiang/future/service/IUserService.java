package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.UserForm;

public interface IUserService {
    ServerResponse register(UserForm userForm);
    ServerResponse login(UserForm userForm);
}
