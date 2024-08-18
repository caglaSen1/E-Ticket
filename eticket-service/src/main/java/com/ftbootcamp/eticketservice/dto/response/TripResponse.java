package com.ftbootcamp.eticketservice.dto.response;

import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripResponse {

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private VehicleType vehicleType;
    private int capacity;
    private double price;
}
