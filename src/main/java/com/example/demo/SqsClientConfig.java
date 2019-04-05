package com.example.demo;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsClientConfig {

    /*
    @Bean("amazonSQS")
    public AmazonSQSAsync testAmazonSQSClient(final SQSRestServer server) {

        final AmazonSQSAsync amazonSQSClient = AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:5500", "eu-west-1"))
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                .build();

        amazonSQSClient.createQueue("sqs-integration-test");

        return amazonSQSClient;
    }

    @Bean
    public SQSRestServer testElasticMQServer() {
        return SQSRestServerBuilder.withPort(5500)
                .withServerAddress(new NodeAddress("http", "localhost", 5500, ""))
                .start();

    }
    */

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(final AmazonSQSAsync sqsClient) {
        return new QueueMessagingTemplate(sqsClient);
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQS){
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQS);
        factory.setMaxNumberOfMessages(10);
        return factory;
    }
}
