package com.ftbootcamp.paymentservice.service;

import com.ftbootcamp.paymentservice.converter.PaymentConverter;
import com.ftbootcamp.paymentservice.dto.request.PaymentGenericRequest;
import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
import com.ftbootcamp.paymentservice.model.Payment;
import com.ftbootcamp.paymentservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.paymentservice.producer.kafka.Log;
import com.ftbootcamp.paymentservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.paymentservice.repository.PaymentRepository;
import com.ftbootcamp.paymentservice.rules.PaymentBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentBusinessRules paymentBusinessRules;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;

    public void createPaymentAndSendQueue(PaymentGenericRequest<?> request) {

        paymentBusinessRules.checkPaymentAmountIsValid(request.getAmount());
        paymentBusinessRules.checkPaymentTypeIsValid(request.getPaymentType());

        // Create payment
        Payment payment = new Payment(request.getPaymentType(), request.getAmount(), request.getUserEmail());
        paymentRepository.save(payment);

        // Send payment to queue
        rabbitMqProducer.sendPaymentToEticketQueue(request);

        // Send log message
        kafkaProducer.sendLogMessage(new Log("Payment created. request: " + request));
    }

    public List<PaymentResponse> getAllPayments() {
        return PaymentConverter.toResponse(paymentRepository.findAll());
    }
}
