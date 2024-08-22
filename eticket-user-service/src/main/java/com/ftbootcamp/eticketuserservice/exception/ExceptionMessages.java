package com.ftbootcamp.eticketuserservice.exception;

import com.ftbootcamp.eticketuserservice.entity.constant.UserEntityConstants;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {
    // User
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USER_ALREADY_EXIST_BY_EMAIL = "User already exist with this email.";
    public static final String USER_EMAIL_NOT_VALID = "Email not valid.";
    public static final String USER_PASSWORD_NOT_VALID = "Password not valid. Please enter a password between " +
            UserEntityConstants.PASSWORD_MIN_LENGTH + " - " + UserEntityConstants.PASSWORD_MAX_LENGTH + " characters.";

    // Role
    public static final String ROLE_NOT_FOUND = "Role not found.";
    public static final String ROLE_ALREADY_EXIST = "Role already exist.";
    public static final String DEFAULT_ROLE_CANNOT_BE_ADDED_REMOVED = "Default role cannot be added or removed.";
}
