package com.ftbootcamp.paymentservice.service;

import com.ftbootcamp.paymentservice.model.Payment;
import com.ftbootcamp.paymentservice.repository.PaymentRepository;
import com.ftbootcamp.paymentservice.rules.PaymentBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentBusinessRules paymentBusinessRules;

    public Payment createPayment(Payment payment, String userEmail, String request) {

        paymentBusinessRules.checkPaymentAmountIsValid(payment.getAmount(), request);
        paymentRepository.save(payment);

        log.info("Payment created. request: {}", request);

        return payment;
    }

    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }
}
