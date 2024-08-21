package com.ftbootcamp.eticketservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripGeneralStatisticsResponse {

    private int totalTripCount;
    private int totalAvailableTripCount;
    private int totalExpiredNotCanceledTripCount;
    private int totalCancelledTripCount;
}
