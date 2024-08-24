package com.ftbootcamp.paymentservice.dto.response;

import com.ftbootcamp.paymentservice.model.enums.PaymentType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentGenericResponse<T> {

    private PaymentType paymentType;
    private BigDecimal amount;
    private String userEmail;
    private T paymentObject;
    private LocalDateTime createdDateTime;
}
