package com.ftbootcamp.paymentservice.converter;

import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
import com.ftbootcamp.paymentservice.model.Payment;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PaymentConverter {

    public static PaymentResponse toResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentType(payment.getPaymentType())
                .amount(payment.getAmount())
                .userEmail(payment.getUserEmail())
                .createdDateTime(payment.getCreatedDateTime())
                .build();
    }

    public static List<PaymentResponse> toResponse(List<Payment> payments) {

        return payments.stream()
                .map(PaymentConverter::toResponse)
                .toList();
    }
}
