package com.ftbootcamp.notificationservice.repository;

import com.ftbootcamp.notificationservice.entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {

}
