package com.ftbootcamp.eticketuserservice.rules;

import com.ftbootcamp.eticketuserservice.entity.User;
import com.ftbootcamp.eticketuserservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.exception.ETicketException;
import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserBusinessRules {

    private final UserRepository userRepository;

    public void checkEmailNull(String email) {
        if (email == null) {
            handleException(ExceptionMessages.USER_EMAIL_CANNOT_BE_NULL, "");
        }
    }

    public void checkIdNull(Long id) {
        if (id == null) {
            handleException(ExceptionMessages.USER_ID_CANNOT_BE_NULL, "");
        }
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

    public void checkPasswordValid(String password) {
        int minPasswordLength = UserEntityConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = UserEntityConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            handleException(ExceptionMessages.USER_PASSWORD_NOT_VALID, "Password: " + password);
        }
    }

    public User checkUserExistById(long id) {
        if (userRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Id: " + id);
        }

        return userRepository.findById(id).get();
    }

    public User checkUserExistByEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            handleException(ExceptionMessages.USER_NOT_FOUND, "Email: " + email);
        }

        return userRepository.findByEmail(email).get();
    }

    public void checkUserTypeAlreadyPremium(String email) {
        if (userRepository.findByEmail(email).get().getUserType().equals(UserType.PREMIUM)) {
            handleException(ExceptionMessages.USER_TYPE_ALREADY_PREMIUM, "Email: " + email);
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
