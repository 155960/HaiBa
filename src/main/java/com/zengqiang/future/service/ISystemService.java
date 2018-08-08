package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.SystemForm;

public interface ISystemService {
    ServerResponse sendSysMessage(SystemForm form);
    ServerResponse getSystemMessage();
}
