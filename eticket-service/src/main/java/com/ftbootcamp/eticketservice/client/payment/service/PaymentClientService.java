package com.ftbootcamp.eticketservice.client.payment.service;

import com.ftbootcamp.eticketservice.client.payment.PaymentClient;
import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentClient paymentClient;

    public void createPayment(PaymentRequest request) {
        paymentClient.createPayment(request);
    }
}
