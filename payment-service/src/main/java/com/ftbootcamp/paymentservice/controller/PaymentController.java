package com.ftbootcamp.paymentservice.controller;

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

    @PostMapping
    @Operation(summary = "Create payment", description = "Create payment with given payment information")
    public GenericResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        return GenericResponse.success(paymentService.createPayment(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Get all payments")
    public GenericResponse<List<PaymentResponse>> getAllPayments() {
        return GenericResponse.success(paymentService.getAllPayments(), HttpStatus.OK);
    }
}
