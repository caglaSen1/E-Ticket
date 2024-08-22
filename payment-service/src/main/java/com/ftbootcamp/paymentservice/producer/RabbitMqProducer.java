package com.ftbootcamp.paymentservice.producer;

import com.ftbootcamp.paymentservice.config.RabbitMQProducerConfig;
import com.ftbootcamp.paymentservice.dto.request.PaymentGenericRequest;
import com.ftbootcamp.paymentservice.dto.response.PaymentGenericResponse;
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

    public void sendPaymentToQueue(PaymentGenericRequest<?> request) {

        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(), rabbitMQProducerConfig.getRoutingKey(),
                request);

        log.info("Message sent to queue. Queue: {}, Message: {}",
                rabbitMQProducerConfig.getQueueName(), request.toString());

    }
}
