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

    public void checkPaymentAmountIsValid(BigDecimal amount, String request){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            handleException(ExceptionMessages.VALID_PAYMENT_AMOUNT, request);
        }
    }

    private void handleException(String exceptionMessage, String request) {
        if (request != null && !request.isEmpty()) {
            log.error("Request: {}, Error: {}", request, exceptionMessage);
        } else {
            log.error("Error: {}", exceptionMessage);
        }
        throw new PaymentException(exceptionMessage);
    }
}
