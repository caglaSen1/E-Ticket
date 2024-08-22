package com.ftbootcamp.eticketservice.exception;

import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.producer.Log;
import com.ftbootcamp.eticketservice.producer.kafka.KafkaProducer;
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
}
