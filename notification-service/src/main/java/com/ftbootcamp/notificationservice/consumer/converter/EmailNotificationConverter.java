package com.ftbootcamp.notificationservice.consumer.converter;

import com.ftbootcamp.notificationservice.dto.request.EmailSendRequest;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EmailNotificationConverter {

    public static EmailSendRequest toEmailSendRequest(NotificationSendRequest notificationSendRequest) {
        return new EmailSendRequest(notificationSendRequest.getTo(), notificationSendRequest.getText());
    }

    /*
    public static EmailSendWithTemplateRequest toEmailSendWithTemplateRequest(
            NotificationSendRequest notificationSendRequest) {
        return new EmailSendWithTemplateRequest(notificationSendRequest.getTo(), notificationSendRequest.getEmailTemplateName());
    }*/
}
