package com.ftbootcamp.paymentservice.rules;

import com.ftbootcamp.paymentservice.exception.ExceptionMessages;
import com.ftbootcamp.paymentservice.exception.PaymentException;
import com.ftbootcamp.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentBusinessRules {

    private final PaymentRepository paymentRepository;

    public void checkPaymentAmountIsValid(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(ExceptionMessages.VALID_PAYMENT_AMOUNT  + "Amount: " + amount);
        }
    }
}
