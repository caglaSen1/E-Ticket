package com.ftbootcamp.eticketuserservice.exception;

public class ETicketException extends RuntimeException {

    private String logMessage;

    public ETicketException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    public ETicketException(String message, String logMessage, Throwable cause) {
        super(message, cause);
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

}
