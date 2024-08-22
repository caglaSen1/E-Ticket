package com.ftbootcamp.paymentservice.converter;
import com.ftbootcamp.paymentservice.dto.request.PaymentRequest;
import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
import com.ftbootcamp.paymentservice.model.Payment;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PaymentConverter {

    public static Payment toEntity(PaymentRequest request) {

        return new Payment(request.getAmount(), request.getUserEmail());
    }

    public static PaymentResponse toResponse(Payment payment) {

        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .createdDateTime(payment.getCreatedDateTime())
                .paymentType(payment.getPaymentType())
                .userEmail(payment.getUserEmail())
                .build();
    }

    public static List<PaymentResponse> toResponse(List<Payment> payments) {

        return payments.stream()
                .map(PaymentConverter::toResponse)
                .toList();
    }
}
