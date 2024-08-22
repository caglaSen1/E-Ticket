package com.ftbootcamp.paymentservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String INVALID_PAYMENT_AMOUNT = "Payment amount must be greater than 0";
    public static final String INVALID_PAYMENT_TYPE = "Invalid payment type. It must be either CREDIT_CARD or EFT.";
}
