package com.ftbootcamp.notificationservice.entity;

import com.ftbootcamp.notificationservice.entity.constant.EntityConstants;
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
@Document(collection = "emails")
public class Email {

    @Id
    private String id;

    @Field(name = EntityConstants.EMAIL)
    private String email;

    @Field(name = EntityConstants.TEXT)
    private String text;

    @Field(name = EntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    public Email(String email, String text) {
        this.email = email;
        this.text = text;
        this.createdDateTime = LocalDateTime.now();
    }
}
