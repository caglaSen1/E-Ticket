package com.ftbootcamp.paymentservice.producer.rabbitmq;

import com.ftbootcamp.paymentservice.config.RabbitMQProducerConfig;
import com.ftbootcamp.paymentservice.dto.request.PaymentGenericRequest;
import com.ftbootcamp.paymentservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.paymentservice.producer.kafka.Log;
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
    private final KafkaProducer kafkaProducer;

    public void sendPaymentToPaymentInfoQueue(PaymentGenericRequest<?> request) {

        rabbitTemplate.convertAndSend(rabbitMQProducerConfig.getExchange(),
                rabbitMQProducerConfig.getEticketRoutingKey(), request);

        kafkaProducer.sendLogMessage(new Log("Message sent to payment info queue. Queue: " +
                rabbitMQProducerConfig.getEticketQueueName()));
    }
}
