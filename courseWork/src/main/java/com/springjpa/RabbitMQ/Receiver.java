package com.springjpa.RabbitMQ;

import com.springjpa.javaMail.JavaMail;
import com.springjpa.jms.Email;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Email email) {
        System.out.println("Received <" + email+ ">");
        JavaMail.sendMessage(email.getTo(),email.getBody());
         latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}
