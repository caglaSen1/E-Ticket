package com.ftbootcamp.notificationservice.consumer.converter;

import com.ftbootcamp.notificationservice.dto.request.EmailSendRequest;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class NotificationConverter {

    public static EmailSendRequest toEmailSendRequest(NotificationSendRequest notificationSendRequest) {
        return new EmailSendRequest(notificationSendRequest.getEmail(), notificationSendRequest.getText());
    }
}
