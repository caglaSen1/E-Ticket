package com.ftbootcamp.notificationservice.repository;

import com.ftbootcamp.notificationservice.entity.Sms;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmsRepository extends MongoRepository<Sms, String> {
}
