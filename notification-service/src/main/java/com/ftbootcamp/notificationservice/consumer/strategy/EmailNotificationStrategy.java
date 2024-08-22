package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.converter.NotificationConverter;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import com.ftbootcamp.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class  EmailNotificationStrategy implements NotificationStrategy {

    private final EmailService emailService;

    @Override
    public void sendNotification(NotificationSendRequest request) {
        emailService.sendEmail(NotificationConverter.toEmailSendRequest(request));

        // TODO: kafka log
    }
}
