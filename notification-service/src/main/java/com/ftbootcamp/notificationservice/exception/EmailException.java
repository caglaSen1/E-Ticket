package com.ftbootcamp.notificationservice.exception;

public class EmailException extends RuntimeException {

    private String logMessage;

    public EmailException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    public EmailException(String message, String logMessage, Throwable cause) {
        super(message, cause);
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }
}
