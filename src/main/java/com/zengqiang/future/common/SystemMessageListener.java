package com.zengqiang.future.common;

import javax.jms.Message;
import javax.jms.MessageListener;

public class SystemMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(message.toString()+"&&&&&&&&&");
    }
}
