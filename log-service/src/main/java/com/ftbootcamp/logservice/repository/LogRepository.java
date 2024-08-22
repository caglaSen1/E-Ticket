package com.ftbootcamp.logservice.repository;

import com.ftbootcamp.logservice.entity.LogMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogMessage, String> {
}
