package com.ftbootcamp.eticketservice.client.payment.dto.request;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentGenericRequest<T> {
    private PaymentType paymentType;
    private BigDecimal amount;
    private String userEmail;
    private T paymentObject;
    private String paymentObjectType;
}
