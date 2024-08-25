package com.ftbootcamp.eticketauthservice.exception;

import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ETicketException.class)
    public GenericResponse handleETicketException(ETicketException e){
        return GenericResponse.failed(e.getMessage());
    }

  /*  @ExceptionHandler(UserClientException.class)
    public GenericResponse handleUserClientException(UserClientException e) {
        return GenericResponse.failed(e.getMessage());
    }*/
}
