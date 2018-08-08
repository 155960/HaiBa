package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.SystemForm;
import com.zengqiang.future.pojo.SystemMessage;
import com.zengqiang.future.service.ISystemService;
import com.zengqiang.future.util.ActiveMQUtil;
import com.zengqiang.future.util.TimeUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Service
public class SystemServeiceImpl implements ISystemService {

    private static final String SYSTOPIC="systemTopic";

    public ServerResponse getSystemMessage(){
        ApplicationContext context=new ClassPathXmlApplicationContext("activemq_consumer.xml");
        System.out.println("接收消息中");
        return null;
    }

    public ServerResponse sendSysMessage(SystemForm form){
        SystemMessage message=formToMessage(form);
        try{
            ActiveMQUtil.sendMessage(SYSTOPIC,message);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("发送失败");
        }

        return ServerResponse.createBySuccessMessage("发送成功");
    }

    private SystemMessage formToMessage(SystemForm form){
        SystemMessage message=new SystemMessage();
        message.setMessage(form.getMessage());
        message.setCreateTime(TimeUtil.formatDate(new Date()));
        return message;
    }

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("activemq_consumer.xml");
        System.out.println("接收消息中");
    }
}
