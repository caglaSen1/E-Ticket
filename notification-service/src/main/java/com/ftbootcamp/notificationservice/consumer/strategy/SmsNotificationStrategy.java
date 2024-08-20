package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendNotification(NotificationSendRequest request) {
        // SMS gönderme işlemi burada simüle ediliyor
        //System.out.println("SMS Gönderildi: " + message);
    }
}