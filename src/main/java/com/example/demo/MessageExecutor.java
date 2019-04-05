package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
@AllArgsConstructor
@Slf4j
public class MessageExecutor {

    @ServiceActivator(inputChannel = "inboundChannel")
    // @SqsListener(value = "sqs-integration-test", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(final String message) throws Exception {
        log.info("Message received: {} ", message);

        // throw new Exception("Fake exception to trigger retry");
    }

}
