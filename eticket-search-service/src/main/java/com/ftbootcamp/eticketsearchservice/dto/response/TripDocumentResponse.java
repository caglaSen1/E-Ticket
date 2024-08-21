package com.ftbootcamp.eticketsearchservice.dto.response;

import com.ftbootcamp.eticketsearchservice.model.enums.VehicleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripDocumentResponse {

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private VehicleType vehicleType;
    private int totalTicketCount;
    private int soldTicketCount;
    private double price;
    private LocalDateTime createdDate;
    private boolean isCancelled;
}
