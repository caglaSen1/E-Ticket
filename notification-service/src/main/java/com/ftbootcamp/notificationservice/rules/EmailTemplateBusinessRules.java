package com.ftbootcamp.notificationservice.rules;

import com.ftbootcamp.notificationservice.entity.EmailTemplate;
import com.ftbootcamp.notificationservice.exception.EmailException;
import com.ftbootcamp.notificationservice.exception.ExceptionMessages;
import com.ftbootcamp.notificationservice.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateBusinessRules {

    private final EmailTemplateRepository emailTemplateRepository;

    public EmailTemplate checkTemplateExistById(String id) {
        if(emailTemplateRepository.findById(id).isEmpty()) {
            throw new EmailException(ExceptionMessages.TEMPLATE_NOT_FOUND + "Id: " + id);
        }

        return emailTemplateRepository.findById(id).get();
    }

    public EmailTemplate checkTemplateExistByName(String name) {
        String nameToUpperCase = name.toUpperCase();

        if(emailTemplateRepository.findByName(nameToUpperCase).isEmpty()) {
            throw new EmailException(ExceptionMessages.TEMPLATE_NOT_FOUND + "Name: " + name);
        }

        return emailTemplateRepository.findByName(nameToUpperCase).get();
    }

    public void checkNameAlreadyExist(String name) {
        String nameToUpperCase = name.toUpperCase();

        if(emailTemplateRepository.findByName(nameToUpperCase).isPresent()) {
            throw new EmailException(ExceptionMessages.TEMPLATE_ALREADY_EXIST_WITH_NAME + "Name: " + name);
        }
    }
}
