package com.ftbootcamp.eticketservice.client.payment;

import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentRequest;
import com.ftbootcamp.eticketservice.client.payment.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "localhost:9069/api/v1/payments")
public interface PaymentClient {

    @PostMapping
    PaymentResponse createPayment(@RequestBody PaymentRequest request);
}
