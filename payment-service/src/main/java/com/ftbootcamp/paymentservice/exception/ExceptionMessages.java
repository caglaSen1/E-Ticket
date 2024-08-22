package com.ftbootcamp.paymentservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String VALID_PAYMENT_AMOUNT = "Payment amount must be greater than 0";
}
