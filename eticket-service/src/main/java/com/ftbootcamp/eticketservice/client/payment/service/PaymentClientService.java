package com.ftbootcamp.eticketservice.client.payment.service;

import com.ftbootcamp.eticketservice.client.payment.PaymentClient;
import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentGenericRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentClient paymentClient;

    public void createPayment(PaymentGenericRequest<?> request) {
        paymentClient.createPayment(request);
    }
}
