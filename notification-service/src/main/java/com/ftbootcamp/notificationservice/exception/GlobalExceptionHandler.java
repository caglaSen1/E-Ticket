package com.ftbootcamp.notificationservice.exception;

import com.ftbootcamp.notificationservice.dto.response.GenericResponse;
import com.ftbootcamp.notificationservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.notificationservice.producer.kafka.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(EmailException.class)
    public GenericResponse handleEmailException(EmailException e){
        kafkaProducer.sendExceptionLogMessage(new Log(e.getMessage()));

        return GenericResponse.failed(e.getMessage());
    }
}
