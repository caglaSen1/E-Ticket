package com.ftbootcamp.paymentservice.producer.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private String message;
    private LocalDateTime createdDateTime;

    public Log(String message) {
        this.message = message;
        this.createdDateTime = LocalDateTime.now();
    }
}