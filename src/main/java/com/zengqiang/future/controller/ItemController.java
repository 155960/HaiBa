package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.service.IItemService;
import com.zengqiang.future.service.IPostService;
import com.zengqiang.future.service.impl.PostServiceImpl;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
@ResponseBody
public class ItemController {

    @Autowired
    private IItemService itemService;

    @RequestMapping("/delete")
    public ServerResponse delete(int itemId){
        return itemService.delete(itemId);
    }

    @RequestMapping("/update")
    public ServerResponse update(@RequestBody ItemForm form){
        return itemService.update(form);
    }
}
