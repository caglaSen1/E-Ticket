package com.ftbootcamp.eticketauthservice.exception;

import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketauthservice.producer.kafka.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaProducer kafkaProducer;

    @ExceptionHandler(ETicketException.class)
    public GenericResponse handleETicketException(ETicketException e){
        kafkaProducer.sendExceptionLogMessage(new Log(e.getMessage()));
        return GenericResponse.failed(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public GenericResponse handleBadCredentialsException(BadCredentialsException e){
        kafkaProducer.sendExceptionLogMessage(new Log(e.getMessage()));
        return GenericResponse.failed(e.getMessage());
    }
}
