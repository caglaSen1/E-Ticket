package com.ftbootcamp.eticketuserservice.rules;

import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketuserservice.exception.ETicketException;
import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketuserservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CompanyUserBusinessRules {

    private final CompanyUserRepository companyUserRepository;
    private final UserRepository userRepository;

    public CompanyUser checkUserExistById(long id) {
        if (companyUserRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Id: " + id);
        }

        return companyUserRepository.findById(id).get();
    }

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

    public CompanyUser checkUserExistByEmail(String email) {
        if (companyUserRepository.findByEmail(email).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Email: " + email);
        }

        return companyUserRepository.findByEmail(email).get();
    }

    public void checkPasswordValid(String password) {
        int minPasswordLength = UserEntityConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = UserEntityConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            handleException(ExceptionMessages.USER_PASSWORD_NOT_VALID, "Password: " + password);
        }
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
