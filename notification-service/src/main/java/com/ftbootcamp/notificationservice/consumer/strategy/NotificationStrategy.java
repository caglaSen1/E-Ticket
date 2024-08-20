package com.ftbootcamp.notificationservice.consumer.strategy;


import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;

public interface NotificationStrategy {
    void sendNotification(NotificationSendRequest request);
}
