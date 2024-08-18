package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripUpdateRequest {

    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private VehicleType vehicleType;
    private int capacity;
    private double price;
}
