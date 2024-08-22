package com.ftbootcamp.paymentservice.exception;

import com.ftbootcamp.paymentservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    public GenericResponse handlePaymentException(PaymentException e){

        return GenericResponse.failed(e.getMessage());
    }
}
