package com.ftbootcamp.logservice.model.documents;

import com.ftbootcamp.logservice.model.constants.EntityConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "exception-log-messages")
public class ExceptionLogMessage {

    @Id
    private String id;

    @Field(name = EntityConstants.MESSAGE)
    private String message;

    @Field(name = EntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

}
