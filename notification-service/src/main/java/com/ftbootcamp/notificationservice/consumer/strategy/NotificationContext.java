package com.ftbootcamp.notificationservice.consumer.strategy;

import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class NotificationContext {

    private NotificationStrategy notificationStrategy;

    public NotificationContext(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public void setNotificationStrategy(NotificationStrategy strategy) {
        this.notificationStrategy = strategy;
    }

    public void send(NotificationSendRequest request) {
        notificationStrategy.sendNotification(request);
    }
}
