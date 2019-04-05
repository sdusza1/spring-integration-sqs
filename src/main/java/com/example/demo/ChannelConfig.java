package com.example.demo;


import com.amazonaws.services.sqs.AmazonSQSAsync;
import lombok.AllArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.support.destination.DynamicQueueUrlDestinationResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
@AllArgsConstructor
public class ChannelConfig {

    public static final String SQS_QUEUE_NAME = "sqs-integration-test";
    public static final int RETRY_NOTIFICATION_AFTER = 5; /* seconds */

    private final AmazonSQSAsync amazonSqs;

    @Bean
    public DirectChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel outboundChannel() {
        return new QueueChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public MessageHandler sqsMessageHandler() {

        final DynamicQueueUrlDestinationResolver destinationResolver = new DynamicQueueUrlDestinationResolver(amazonSqs, null);
        destinationResolver.setAutoCreate(true);

        final SqsMessageHandler messageHandler = new SqsMessageHandler(amazonSqs, destinationResolver);
        messageHandler.setQueue(SQS_QUEUE_NAME);

        return messageHandler;
    }

    @Bean
    public MessageProducerSupport sqsMessageDrivenChannelAdapter() {
        SqsMessageDrivenChannelAdapter adapter = new SqsMessageDrivenChannelAdapter(amazonSqs, SQS_QUEUE_NAME);
        adapter.setOutputChannel(inboundChannel());
        adapter.setMessageDeletionPolicy(SqsMessageDeletionPolicy.ON_SUCCESS);
        adapter.setMaxNumberOfMessages(10);
        adapter.setVisibilityTimeout(RETRY_NOTIFICATION_AFTER);
        return adapter;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(200));
        return pollerMetadata;
    }
}
