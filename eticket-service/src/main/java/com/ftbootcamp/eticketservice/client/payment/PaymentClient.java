package com.ftbootcamp.eticketservice.client.payment;

import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentGenericRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${payment-service.name}", url = "${payment-service.url}")
public interface PaymentClient {

    @PostMapping("/process-and-send-queue")
    void processPaymentRequestAndSendToQueue(@RequestBody PaymentGenericRequest<?> request);
}