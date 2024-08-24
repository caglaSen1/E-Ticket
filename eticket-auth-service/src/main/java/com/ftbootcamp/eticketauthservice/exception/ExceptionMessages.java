package com.ftbootcamp.eticketauthservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "User not found. ";
    public static final String FAILED_TO_CONNECT_FEIGN_USER = "Failed to connect to user service. " +
            "Please try again later.";
    public static final String FAILED_TO_CREATE = "Failed to create user. ";
}
