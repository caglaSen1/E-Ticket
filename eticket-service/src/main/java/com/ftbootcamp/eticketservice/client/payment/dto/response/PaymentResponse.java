package com.ftbootcamp.eticketservice.client.payment.dto.response;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {
    private BigDecimal amount;
    private LocalDateTime createdDateTime;
    private PaymentType paymentType;
    private String userEmail;
}
