package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.payment.enums.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketMultipleBuyRequest {

    private PaymentType paymentType;
    private List<PassengerTicketRequest> passengerTicketRequests;
}
