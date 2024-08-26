package com.ftbootcamp.eticketauthservice.exception;

import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "User not found. ";
    public static final String USER_ALREADY_EXIST_BY_EMAIL = "User already exist with this email.";
    public static final String USER_EMAIL_NOT_VALID = "Email not valid.";
    public static final String USER_PASSWORD_NOT_VALID = "Password not valid. Please enter a password between " +
            PasswordConstants.PASSWORD_MIN_LENGTH + " - " + PasswordConstants.PASSWORD_MAX_LENGTH + " characters.";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password. Please try again.";
}
