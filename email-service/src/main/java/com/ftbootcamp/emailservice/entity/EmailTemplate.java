package com.ftbootcamp.emailservice.entity;

import com.ftbootcamp.emailservice.entity.constant.EntityConstants;
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
@Document(collection = "email-templates")
public class EmailTemplate {

    @Id
    private String id;

    @Field(name = EntityConstants.NAME)
    private String name;

    @Field(name = EntityConstants.TEXT)
    private String text;

    @Field(name = EntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    public EmailTemplate(String name, String text) {
        this.name = name.toUpperCase();
        this.text = text;
        this.createdDateTime = LocalDateTime.now();
    }
}
