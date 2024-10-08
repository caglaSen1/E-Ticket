package com.ftbootcamp.paymentservice.dto.request;

import com.ftbootcamp.paymentservice.model.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentGenericRequest<T> {

    private PaymentType paymentType;
    private BigDecimal amount;
    private String userEmail;
    private T paymentObject;
    private String paymentObjectType;
}
