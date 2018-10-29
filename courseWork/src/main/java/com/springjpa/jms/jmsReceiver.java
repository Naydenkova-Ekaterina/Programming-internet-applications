package com.springjpa.jms;

import com.springjpa.RabbitMQ.Receiver;
import com.springjpa.SpringJpaPostgreSqlApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class jmsReceiver {


   private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final ConfigurableApplicationContext context;

    public jmsReceiver(Receiver receiver, RabbitTemplate rabbitTemplate,
                       ConfigurableApplicationContext context){
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;

    }


    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email)
    {
        System.out.println("Received <" + email + ">");

        try {
            System.out.println("Sending message...");
            rabbitTemplate.convertAndSend(SpringJpaPostgreSqlApplication.queueName, email);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            context.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
