package com.ftbootcamp.emailservice.rules;

import com.ftbootcamp.emailservice.entity.EmailTemplate;
import com.ftbootcamp.emailservice.exception.EmailException;
import com.ftbootcamp.emailservice.exception.ExceptionMessages;
import com.ftbootcamp.emailservice.repository.EmailTemplateRepository;
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
            handleException(ExceptionMessages.TEMPLATE_NOT_FOUND, "Id: " + id);
        }

        return emailTemplateRepository.findById(id).get();
    }

    public EmailTemplate checkTemplateExistByName(String name) {
        String nameToUpperCase = name.toUpperCase();

        if(emailTemplateRepository.findByName(nameToUpperCase).isEmpty()) {
            handleException(ExceptionMessages.TEMPLATE_NOT_FOUND, "Name: " + name);
        }

        return emailTemplateRepository.findByName(nameToUpperCase).get();
    }

    public void checkNameAlreadyExist(String name) {
        String nameToUpperCase = name.toUpperCase();

        if(emailTemplateRepository.findByName(nameToUpperCase).isPresent()) {
            handleException(ExceptionMessages.TEMPLATE_ALREADY_EXIST_WITH_NAME, "Name: " + name);
        }
    }

    private void handleException(String exceptionMessage, String request) {
        String logMessage;

        if (request != null && !request.isEmpty()) {
            logMessage = String.format("Log: Error: %s, Request: %s", exceptionMessage, request);
        } else {
            logMessage = String.format("Log: Error: %s", exceptionMessage);
        }

        throw new EmailException(exceptionMessage, logMessage);
    }

}
