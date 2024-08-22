package com.ftbootcamp.paymentservice.exception;

import com.ftbootcamp.paymentservice.dto.response.GenericResponse;
import com.ftbootcamp.paymentservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.paymentservice.producer.kafka.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(PaymentException.class)
    public GenericResponse handlePaymentException(PaymentException e){

        kafkaProducer.sendExceptionLogMessage(new Log(e.getMessage()));
        return GenericResponse.failed(e.getMessage());
    }
}
