package com.ftbootcamp.eticketservice.client.payment.service;

import com.ftbootcamp.eticketservice.client.payment.PaymentClient;
import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentGenericRequest;
import com.ftbootcamp.eticketservice.exception.PaymentClientException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentClient paymentClient;

    public void createPayment(PaymentGenericRequest<?> request) {
        try{
            paymentClient.createPayment(request);
        } catch (FeignException e){
            throw new PaymentClientException("Failed to connect to payment service. Please try again later.");
        }

    }
}
