package com.ftbootcamp.paymentservice.service;

import com.ftbootcamp.paymentservice.converter.PaymentConverter;
import com.ftbootcamp.paymentservice.dto.request.PaymentRequest;
import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
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

    public PaymentResponse createPayment(PaymentRequest request) {

        paymentBusinessRules.checkPaymentAmountIsValid(request.getAmount());

        Payment payment = new Payment(request.getPaymentType(), request.getAmount(), request.getUserEmail());
        paymentRepository.save(payment);

        log.info("Payment created. request: {}", request);

        return PaymentResponse.builder()
                .paymentType(payment.getPaymentType())
                .amount(payment.getAmount())
                .userEmail(payment.getUserEmail())
                .paymentObject(request.getPaymentObject())
                .createdDateTime(payment.getCreatedDateTime())
                .build();
    }

    public List<PaymentResponse> getAllPayments() {
        return PaymentConverter.toResponse(paymentRepository.findAll());
    }
}
