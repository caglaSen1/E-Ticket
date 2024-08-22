package com.ftbootcamp.paymentservice.controller;

import com.ftbootcamp.paymentservice.converter.PaymentConverter;
import com.ftbootcamp.paymentservice.dto.request.PaymentRequest;
import com.ftbootcamp.paymentservice.dto.response.GenericResponse;
import com.ftbootcamp.paymentservice.dto.response.PaymentResponse;
import com.ftbootcamp.paymentservice.model.Payment;
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
        Payment payment = PaymentConverter.toEntity(request);
        PaymentResponse paymentResponse = PaymentConverter
                .toResponse(paymentService.createPayment(payment, request.getUserEmail(), request.toString()));

        return GenericResponse.success(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Get all payments")
    public GenericResponse<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> paymentResponseList = PaymentConverter.toResponse(paymentService.getAllPayments());
        return GenericResponse.success(paymentResponseList, HttpStatus.OK);
    }
}
