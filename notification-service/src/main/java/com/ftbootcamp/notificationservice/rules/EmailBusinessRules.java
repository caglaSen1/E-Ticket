package com.ftbootcamp.notificationservice.rules;

import com.ftbootcamp.notificationservice.entity.Email;
import com.ftbootcamp.notificationservice.exception.EmailException;
import com.ftbootcamp.notificationservice.exception.ExceptionMessages;
import com.ftbootcamp.notificationservice.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailBusinessRules {

    private final EmailRepository emailRepository;

    public Email checkEmailExistById(String id) {
        if(emailRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.EMAIL_NOT_FOUND, "Id: " + id);
        }

        return emailRepository.findById(id).get();
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
