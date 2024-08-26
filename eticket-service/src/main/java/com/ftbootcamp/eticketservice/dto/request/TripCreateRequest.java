package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripCreateRequest {

    @NotNull(message = "Departure time is mandatory")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is mandatory")
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Departure city is mandatory")
    private String departureCity;

    @NotBlank(message = "Arrival city is mandatory")
    private String arrivalCity;

    @NotNull(message = "Vehicle type is mandatory")
    private VehicleType vehicleType;

    @NotNull(message = "Vehicle type is mandatory")
    private double price;
}
