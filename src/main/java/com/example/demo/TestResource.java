package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestResource {

    private final MessagesGateway gateway;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @RequestMapping( value = "send", method = RequestMethod.GET)
    public void send() {

    gateway.sendMessage("TEST");

    /*
        for(int i=0; i < 15000; i++) {
            // gateway.sendMessage("TEST "+ i);
            queueMessagingTemplate.send("sqs-integration-test", MessageBuilder.withPayload("XXX: "+ i).build());
        }
    */

    }

}
