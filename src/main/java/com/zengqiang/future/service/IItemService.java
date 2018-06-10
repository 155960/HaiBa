package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.ItemForm;

public interface IItemService {
    ServerResponse delete(int itemId);
    ServerResponse update(ItemForm form);
}
