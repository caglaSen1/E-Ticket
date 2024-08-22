package com.ftbootcamp.notificationservice.repository;

import com.ftbootcamp.notificationservice.entity.PushNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PushNotificationRepository extends MongoRepository<PushNotification, String> {
}
