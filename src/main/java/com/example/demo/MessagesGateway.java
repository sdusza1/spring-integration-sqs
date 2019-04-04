package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessagesGateway {

    final private QueueChannel outboundChannel;

    public MessagesGateway(@Lazy final QueueChannel outboundChannel) {
        this.outboundChannel = outboundChannel;
    }

    public void sendMessage(final String message) {
       outboundChannel.send(MessageBuilder.withPayload(message).build());
    }

}
