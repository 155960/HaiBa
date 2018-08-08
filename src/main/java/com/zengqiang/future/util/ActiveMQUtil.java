package com.zengqiang.future.util;

import com.zengqiang.future.common.PushMessgeListener;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

@Component
public class ActiveMQUtil {

    public static JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate template){
        ActiveMQUtil.jmsTemplate=template;
    }

    public static void createQueueProducer(String name){

    }

    public static void createTopicProducer(String name){
        ActiveMQTopic topic=new ActiveMQTopic(name);
    }

    public static void sendMessage(String topic, Serializable m){
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message=session.createObjectMessage(m);
                return message;
            }
        });
    }

    public static void subscribe(){
        jmsTemplate.receive();


    }
}
