package com.ftbootcamp.notificationservice.repository;

import com.ftbootcamp.notificationservice.entity.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {

    @Query("{ 'name' : ?0 }")
    Optional<EmailTemplate> findByName(String name);


}



