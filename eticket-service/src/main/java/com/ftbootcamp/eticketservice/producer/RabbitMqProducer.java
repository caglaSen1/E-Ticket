package com.ftbootcamp.eticketservice.producer;

import com.ftbootcamp.eticketservice.config.RabbitMQProducerConfig;
import com.ftbootcamp.eticketservice.producer.dto.NotificationSendRequest;
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

    public void sendTicketInfoMessage(NotificationSendRequest notificationSendRequest) {

        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(), rabbitMQProducerConfig.getRoutingKey(),
                notificationSendRequest);

        log.info("Message sent to queue. Queue: {}, Message: {}",
                rabbitMQProducerConfig.getQueueName(), notificationSendRequest.toString());

    }
}
