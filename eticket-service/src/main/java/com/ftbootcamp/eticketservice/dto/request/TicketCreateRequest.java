package com.ftbootcamp.eticketservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketCreateRequest {

    private long tripId;
    private int seatNumber;
}
