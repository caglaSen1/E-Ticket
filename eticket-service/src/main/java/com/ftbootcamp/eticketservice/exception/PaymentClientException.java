package com.ftbootcamp.eticketservice.exception;

public class PaymentClientException extends RuntimeException{

    public PaymentClientException(String message) {
        super(message);
    }
}
