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
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is mandatory")
    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Departure city is mandatory")
    @Size(max = 100, message = "Departure city must not exceed 100 characters")
    private String departureCity;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(max = 100, message = "Arrival city must not exceed 100 characters")
    private String arrivalCity;

    @NotNull(message = "Vehicle type is mandatory")
    private VehicleType vehicleType;

    @Positive(message = "Price must be a positive number")
    private double price;
}
