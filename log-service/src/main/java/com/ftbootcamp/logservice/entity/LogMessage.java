package com.ftbootcamp.logservice.entity;

import com.ftbootcamp.logservice.entity.constants.EntityConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "log-messages")
public class LogMessage {

    @Id
    private String id;

    @Field(name = EntityConstants.MESSAGE)
    private String message;

    @Field(name = EntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    public LogMessage(String logMessage) {
        this.message = logMessage;
        this.createdDateTime = LocalDateTime.now();
    }
}