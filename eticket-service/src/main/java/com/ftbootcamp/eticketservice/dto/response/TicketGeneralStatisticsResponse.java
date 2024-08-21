package com.ftbootcamp.eticketservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TicketGeneralStatisticsResponse {

    private int totalTicketCount;
    private int totalAvailableTicketCount;
    private int totalExpiredTicketCount;
    private int totalSoldTicketCount;
    private double totalSoldTicketPrice;
}
