package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketBuyRequest {

    private PaymentType paymentType;
    private long userId;
    private long ticketId;
}
