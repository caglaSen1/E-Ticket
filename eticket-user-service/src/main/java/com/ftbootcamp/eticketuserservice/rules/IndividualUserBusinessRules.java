package com.ftbootcamp.eticketuserservice.rules;

import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.constant.UserEntityConstants;
import com.ftbootcamp.eticketuserservice.exception.ETicketException;
import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketuserservice.repository.IndividualUserRepository;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class IndividualUserBusinessRules {

    private final IndividualUserRepository individualUserRepository;
    private final UserRepository userRepository;

    public IndividualUser checkUserExistById(long id) {
        if (individualUserRepository.findById(id).isEmpty()) {
            throw new ETicketException(ExceptionMessages.USER_NOT_FOUND + " Id: " + id);
        }

        return individualUserRepository.findById(id).get();
    }

    public void checkEmailValid(String email) {
        if (!email.contains("@")) {
            throw new ETicketException(ExceptionMessages.USER_EMAIL_NOT_VALID + " Email: " + email);
        }
    }

    public void checkEmailAlreadyExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ETicketException(ExceptionMessages.USER_ALREADY_EXIST_BY_EMAIL + " Email: " + email);
        }
    }

    public IndividualUser checkUserExistByEmail(String email) {
        if (individualUserRepository.findByEmail(email).isEmpty()) {
            throw new ETicketException(ExceptionMessages.USER_NOT_FOUND + " Email: " + email);
        }

        return individualUserRepository.findByEmail(email).get();
    }

    public void checkPasswordValid(String password) {
        int minPasswordLength = UserEntityConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = UserEntityConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            throw new ETicketException(ExceptionMessages.USER_PASSWORD_NOT_VALID + " Password: " + password);
        }
    }
}
