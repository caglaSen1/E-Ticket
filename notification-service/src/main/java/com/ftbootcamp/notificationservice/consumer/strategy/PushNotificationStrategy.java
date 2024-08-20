package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationStrategy implements NotificationStrategy {

    @Override
    public void sendNotification(NotificationSendRequest request) {
        // Push bildirim gönderme işlemi burada simüle ediliyor
        //System.out.println("Push Bildirimi Gönderildi: " + message);
    }
}