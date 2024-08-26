package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketMultipleBuyRequest {

    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;

    @NotEmpty(message = "Passenger ticket requests list cannot be empty")
    private List<PassengerTicketRequest> passengerTicketRequests;
}
