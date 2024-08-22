package com.ftbootcamp.paymentservice.producer;

import com.ftbootcamp.paymentservice.config.RabbitMQProducerConfig;
import com.ftbootcamp.paymentservice.dto.request.PaymentGenericRequest;
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

    public void sendPaymentToEticketQueue(PaymentGenericRequest<?> request) {

        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(),
                rabbitMQProducerConfig.getEticketRoutingKey(), request);

        log.info("Message sent to queue. Queue: {}, Message: {}",
                rabbitMQProducerConfig.getEticketQueueName(), request.toString());

    }
}
