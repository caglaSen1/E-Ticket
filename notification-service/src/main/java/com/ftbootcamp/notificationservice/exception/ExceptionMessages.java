package com.ftbootcamp.notificationservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    // Email Template
    public static final String TEMPLATE_NOT_FOUND = "Email template not found";
    public static final String TEMPLATE_ALREADY_EXIST_WITH_NAME = "Email template already exist with this name";

    // Email
    public static final String EMAIL_NOT_FOUND = "Email not found";
}
