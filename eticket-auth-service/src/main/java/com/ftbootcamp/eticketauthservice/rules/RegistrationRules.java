package com.ftbootcamp.eticketauthservice.rules;

import com.ftbootcamp.eticketauthservice.constants.PasswordConstants;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegistrationRules {

    private final UserRepository userRepository;

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

    public void checkPasswordValid(String password) {
        int minPasswordLength = PasswordConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = PasswordConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            throw new ETicketException(ExceptionMessages.USER_PASSWORD_NOT_VALID + " Password: " + password);
        }
    }
}
