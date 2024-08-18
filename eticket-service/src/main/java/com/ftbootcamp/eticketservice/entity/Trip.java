package com.ftbootcamp.eticketservice.entity;

import com.ftbootcamp.eticketservice.entity.constant.TripEntityConstants;
import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "trip")
    private List<Ticket> tickets;

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

    @Column(name = TripEntityConstants.CAPACITY, nullable = false)
    private int capacity;

    @Column(name = TripEntityConstants.PRICE, nullable = false)
    private double price;

    @Column(name = TripEntityConstants.CREATED_DATE)
    private LocalDateTime createdDate;
}
