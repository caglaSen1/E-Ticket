package com.ftbootcamp.emailservice.exception;

import com.ftbootcamp.emailservice.dto.response.GenericResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailException.class)
    public GenericResponse handleEmailException(EmailException e){
        return GenericResponse.failed(e.getMessage());
    }
}
