package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.dto.request.EmailSendRequest;
import com.ftbootcamp.notificationservice.service.EmailService;
import com.ftbootcamp.notificationservice.consumer.converter.EmailNotificationConverter;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class  EmailNotificationStrategy implements NotificationStrategy {

    private final EmailService emailService;

    @Override
    public void sendNotification(NotificationSendRequest request) {
        EmailSendRequest emailSendRequest = EmailNotificationConverter.toEmailSendRequest(request);
        emailService.sendEmail(emailSendRequest);
        System.out.println("E-posta Gönderildi.");
    }
}
