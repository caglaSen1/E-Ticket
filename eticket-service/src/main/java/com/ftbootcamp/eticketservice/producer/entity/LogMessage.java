package com.ftbootcamp.eticketservice.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {

    private String message;
    private LocalDateTime createdDateTime;

    public LogMessage(String logMessage) {
        this.message = logMessage;
        this.createdDateTime = LocalDateTime.now();
    }
}