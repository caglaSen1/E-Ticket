package com.ftbootcamp.eticketservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketBuyRequest {

    private long userId;
    private long ticketId;

}
