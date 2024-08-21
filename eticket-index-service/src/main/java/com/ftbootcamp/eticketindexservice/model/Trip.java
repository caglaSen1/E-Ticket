package com.ftbootcamp.eticketindexservice.model;

import com.ftbootcamp.eticketindexservice.model.enums.VehicleType;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Trip {

    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private VehicleType vehicleType;
    private int totalTicketCount;
    private int soldTicketCount;
    private double price;
    private LocalDateTime createdDate;
    private boolean isCancelled = false;

}
