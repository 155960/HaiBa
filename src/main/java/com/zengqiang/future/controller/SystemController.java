package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.SystemForm;
import com.zengqiang.future.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统管理
 * 消息推送
 *
 */
@Controller
@RequestMapping("sys")
@ResponseBody
public class SystemController {

    @Autowired
    private ISystemService systemService;

    @RequestMapping(value = "get_sys_message",method = RequestMethod.POST)
    public ServerResponse getSystemMessage(){
        systemService.getSystemMessage();
        return null;
    }

    @RequestMapping(value = "send_sys_message",method = RequestMethod.POST)
    public ServerResponse sendSysMessage(@RequestBody SystemForm form){
        return systemService.sendSysMessage(form);
    }
}
