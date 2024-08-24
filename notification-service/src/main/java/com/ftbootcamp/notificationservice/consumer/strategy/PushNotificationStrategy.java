package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import com.ftbootcamp.notificationservice.entity.PushNotification;
import com.ftbootcamp.notificationservice.repository.PushNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PushNotificationStrategy implements NotificationStrategy {

    private final PushNotificationRepository pushNotificationRepository;

    @Override
    public void sendNotification(NotificationSendRequest request) {
        // Push notification sending codes...

        pushNotificationRepository.save(new PushNotification(request.getText()));
    }
}