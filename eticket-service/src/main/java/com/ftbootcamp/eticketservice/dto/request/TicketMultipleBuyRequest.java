package com.ftbootcamp.eticketservice.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketMultipleBuyRequest {

    private long buyerId;
    private List<PassengerTicketRequest> passengerTicketRequests;
}
