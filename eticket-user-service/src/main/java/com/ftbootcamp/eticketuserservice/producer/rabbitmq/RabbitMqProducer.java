package com.ftbootcamp.eticketuserservice.producer.rabbitmq;

import com.ftbootcamp.eticketuserservice.config.RabbitMQProducerConfig;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqProducer {

    private final AmqpTemplate rabbitTemplate;

    private final RabbitMQProducerConfig rabbitMQProducerConfig;

    public void sendMessage(NotificationSendRequest notificationSendRequest) {

        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(), rabbitMQProducerConfig.getRoutingKey(),
                notificationSendRequest);

        log.info("Message sent to queue. Queue: {}, Message: {}",
                rabbitMQProducerConfig.getQueueName(), notificationSendRequest.toString());

    }
}
