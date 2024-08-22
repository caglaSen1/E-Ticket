package com.ftbootcamp.logservice.repository;

import com.ftbootcamp.logservice.model.documents.ExceptionLogMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExceptionLogRepository extends MongoRepository<ExceptionLogMessage, String> {
}
