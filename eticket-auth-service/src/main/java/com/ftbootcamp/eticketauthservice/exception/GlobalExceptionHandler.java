package com.ftbootcamp.eticketauthservice.exception;

import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketauthservice.producer.kafka.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
