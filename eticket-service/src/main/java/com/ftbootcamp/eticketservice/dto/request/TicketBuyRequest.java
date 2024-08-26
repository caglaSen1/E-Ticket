package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketBuyRequest {

    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;

    @NotNull(message = "Ticket ID is mandatory")
    @Positive(message = "Ticket ID must be a positive number")
    private Long ticketId;
}
