package com.ftbootcamp.eticketuserservice.entity.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class UserEntityConstants {

    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PASSWORD = "password";
    public static final String CREATED_DATE = "created_date";

    public static final String NATIONAL_ID = "national_id";
    public static final String TAX_NUMBER = "tax_number";

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMPANY_NAME = "company_name";

    public static final String BIRTH_DATE = "birth_date";

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 12;
}
