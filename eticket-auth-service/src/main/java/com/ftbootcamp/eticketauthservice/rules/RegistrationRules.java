package com.ftbootcamp.eticketauthservice.rules;

import com.ftbootcamp.eticketauthservice.repository.AdminUserRepository;
import com.ftbootcamp.eticketauthservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketauthservice.repository.IndividualUserRepository;
import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegistrationRules {

    private final AdminUserRepository adminUserRepository;
    private final CompanyUserRepository companyUserRepository;
    private final IndividualUserRepository individualUserRepository;

    public void checkEmailValid(String email) {
        if (!email.contains("@")) {
            throw new ETicketException(ExceptionMessages.USER_EMAIL_NOT_VALID + " Email: " + email);
        }
    }

    public void checkEmailAlreadyExist(String email) {
        if (adminUserRepository.findByEmail(email).isPresent() ||
                companyUserRepository.findByEmail(email).isPresent()||
                individualUserRepository.findByEmail(email).isPresent()) {
            throw new ETicketException(ExceptionMessages.USER_ALREADY_EXIST_BY_EMAIL + " Email: " + email);
        }
    }

    public void checkPasswordValid(String password) {
        int minPasswordLength = PasswordConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = PasswordConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            throw new ETicketException(ExceptionMessages.USER_PASSWORD_NOT_VALID);
        }
    }
}
