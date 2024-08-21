package com.ftbootcamp.eticketservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripStatisticsResponse {

    private int totalTicketCount;
    private int soldTicketCount;
    private double totalSoldTicketPrice;
}
