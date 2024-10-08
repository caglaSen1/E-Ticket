package com.ftbootcamp.paymentservice.controller;

import com.ftbootcamp.paymentservice.dto.request.PaymentGenericRequest;
import com.ftbootcamp.paymentservice.dto.request.PaymentRequest;
import com.ftbootcamp.paymentservice.dto.response.GenericResponse;
import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
import com.ftbootcamp.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Template API V1", description = "Payment Template API for payment operations")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process-and-send-queue")
    @Operation(summary = "Create payment and send to queue",
            description = "Create payment with given payment information and send to queue for processes after payment")
    public GenericResponse<Void> processPaymentRequestAndSendToQueue(@RequestBody PaymentGenericRequest<?> request) {
        paymentService.createPaymentAndSendQueue(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @GetMapping("/admin-panel/all")
    @Operation(summary = "Get all payments", description = "Get all payments")
    public GenericResponse<List<PaymentResponse>> getAllPayments() {
        return GenericResponse.success(paymentService.getAllPayments(), HttpStatus.OK);
    }
}
