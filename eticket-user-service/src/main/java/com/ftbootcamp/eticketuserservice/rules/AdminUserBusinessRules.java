package com.ftbootcamp.eticketuserservice.rules;

import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketuserservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketuserservice.exception.ETicketException;
import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketuserservice.repository.AdminUserRepository;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class AdminUserBusinessRules {

    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;

    public void checkEmailValid(String email) {
        if (!email.contains("@")) {
            handleException(ExceptionMessages.USER_EMAIL_NOT_VALID, "Email: " + email);
        }
    }

    public void checkEmailAlreadyExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            handleException(ExceptionMessages.USER_ALREADY_EXIST_BY_EMAIL, "Email: " + email);
        }
    }

    public void checkPasswordValid(String password) {
        int minPasswordLength = UserEntityConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = UserEntityConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            handleException(ExceptionMessages.USER_PASSWORD_NOT_VALID, "Password: " + password);
        }
    }

    public AdminUser checkUserExistById(long id) {
        if (adminUserRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Id: " + id);
        }

        return adminUserRepository.findById(id).get();
    }

    public AdminUser checkUserExistByEmail(String email) {
        if (adminUserRepository.findByEmail(email).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Email: " + email);
        }

        return adminUserRepository.findByEmail(email).get();
    }

    private void handleException(String exceptionMessage, String request) {
        String logMessage;

        if (request != null && !request.isEmpty()) {
            logMessage = String.format("Log: Error: %s, Request: %s", exceptionMessage, request);
        } else {
            logMessage = String.format("Log: Error: %s", exceptionMessage);
        }

        throw new ETicketException(exceptionMessage, logMessage);
    }
}
