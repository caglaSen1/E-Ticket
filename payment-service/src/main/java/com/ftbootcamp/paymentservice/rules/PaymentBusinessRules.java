package com.ftbootcamp.paymentservice.rules;

import com.ftbootcamp.paymentservice.exception.ExceptionMessages;
import com.ftbootcamp.paymentservice.exception.PaymentException;
import com.ftbootcamp.paymentservice.model.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentBusinessRules {

    public void checkPaymentAmountIsValid(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(ExceptionMessages.INVALID_PAYMENT_AMOUNT  + " Amount: " + amount);
        }
    }

    public void checkPaymentTypeIsValid(PaymentType paymentType) {
        if (paymentType != PaymentType.CREDIT_CARD && paymentType != PaymentType.EFT) {
            throw new PaymentException(ExceptionMessages.INVALID_PAYMENT_TYPE + " Payment Type: " + paymentType);
        }
    }
}
