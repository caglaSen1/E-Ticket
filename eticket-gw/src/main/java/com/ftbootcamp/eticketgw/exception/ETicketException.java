package com.ftbootcamp.eticketgw.exception;

public class ETicketException extends RuntimeException {

    public ETicketException(String message) {
        super(message);
    }

    public ETicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
