package com.ftbootcamp.eticketservice.entity;

import com.ftbootcamp.eticketservice.entity.constant.TripEntityConstants;
import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TripEntityConstants.DEPARTURE_TIME, nullable = false)
    private LocalDateTime departureTime;

    @Column(name = TripEntityConstants.ARRIVAL_TIME)
    private LocalDateTime arrivalTime;

    @Column(name = TripEntityConstants.DEPARTURE_CITY, nullable = false)
    private String departureCity;

    @Column(name = TripEntityConstants.ARRIVAL_CITY, nullable = false)
    private String arrivalCity;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = TripEntityConstants.AVAILABLE_SEATS, nullable = false)
    private int availableSeats;

    @Column(name = TripEntityConstants.PRICE, nullable = false)
    private double price;

    @Column(name = TripEntityConstants.CREATED_DATE)
    private LocalDateTime createdDate;
}
