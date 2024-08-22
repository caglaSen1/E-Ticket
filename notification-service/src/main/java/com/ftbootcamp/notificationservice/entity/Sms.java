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
@Document(collection = "sms")
public class Sms {

    @Id
    private String id;

    @Field(name = EntityConstants.PHONE_NUMBER)
    private String phoneNumber;

    @Field(name = EntityConstants.TEXT)
    private String text;

    @Field(name = EntityConstants.CREATED_DATE_TIME)
    private LocalDateTime createdDateTime;

    public Sms(String phoneNumber, String text) {
        this.phoneNumber = phoneNumber;
        this.text = text;
        this.createdDateTime = LocalDateTime.now();
    }
}
