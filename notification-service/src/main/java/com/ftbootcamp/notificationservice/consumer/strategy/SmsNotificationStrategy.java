package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import com.ftbootcamp.notificationservice.entity.Email;
import com.ftbootcamp.notificationservice.entity.Sms;
import com.ftbootcamp.notificationservice.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    private final SmsRepository smsRepository;

    @Override
    public void sendNotification(NotificationSendRequest request) {
        // SMS sending codes...

        smsRepository.save(new Sms(request.getPhoneNumber(), request.getText()));
    }
}