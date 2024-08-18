package com.ftbootcamp.eticketuserservice.exception;

import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ETicketException.class)
    public GenericResponse handleETicketException(ETicketException e){
        return GenericResponse.failed(e.getMessage());
    }
}
