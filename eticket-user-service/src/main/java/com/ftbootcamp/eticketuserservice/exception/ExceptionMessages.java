package com.ftbootcamp.eticketuserservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String ROLE_NAME_CANNOT_BE_NULL = "Role name cannot be null.";
    public static final String ROLE_NOT_FOUND = "Role not found.";
    public static final String ROLE_ALREADY_EXIST = "Role already exist.";
}
