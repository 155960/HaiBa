package com.zengqiang.future.service.impl;

import com.zengqiang.future.service.IProduceService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Service
public class ProduceServiceImpl implements IProduceService {
    @Override
    public void sendMessage(String message) {

    }

    /*@Autowired
    JmsTemplate jmsTemplate;



    @Autowired
    ConnectionFactory connectionFactory;

    @Override
    public void sendMessage(final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage=session.createTextMessage(message);

                return textMessage;
            }
        });
        System.out.println("发送消息"+message);
    }

    public void test(){
        try {
            Connection connection=connectionFactory.createConnection();
            connection.start();
            //第一参数为事务自动应答模式
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination=session.createTopic("a");
            MessageProducer producer=session.createProducer(destination);
            connection.close();

            MessageConsumer consumer=session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }*/
}
