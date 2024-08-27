package com.ftbootcamp.eticketservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketResponse implements java.io.Serializable{

    private TripResponse trip;
    private String seatNo;
    private String passengerEmail;
    private double price;
    private boolean isSold;
}
