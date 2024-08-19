package com.ftbootcamp.eticketservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketResponse {

    private TripResponse trip;
    private String seatNo;
    private String passengerEmail;
    private double price;
    private boolean isBought;
}
