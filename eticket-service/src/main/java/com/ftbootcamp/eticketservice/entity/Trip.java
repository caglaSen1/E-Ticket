package com.ftbootcamp.eticketservice.entity;

import com.ftbootcamp.eticketservice.entity.constant.TripEntityConstants;
import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TripEntityConstants.DEPARTURE_TIME, nullable = false)
    private LocalDateTime departureTime;

    @Column(name = TripEntityConstants.ARRIVAL_TIME, nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = TripEntityConstants.DEPARTURE_CITY, nullable = false)
    private String departureCity;

    @Column(name = TripEntityConstants.ARRIVAL_CITY, nullable = false)
    private String arrivalCity;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = TripEntityConstants.TOTAL_TICKET_COUNT, nullable = false)
    private int totalTicketCount;

    @Column(name = TripEntityConstants.SOLD_TICKET_COUNT, nullable = false)
    private int soldTicketCount;

    @Column(name = TripEntityConstants.PRICE, nullable = false)
    private double price;

    @Column(name = TripEntityConstants.CREATED_DATE)
    private LocalDateTime createdDate;

    @Column(name = "is_cancelled", nullable = false)
    private boolean isCancelled = false;

    public Trip(LocalDateTime departureTime, LocalDateTime arrivalTime, String departureCity, String arrivalCity,
                VehicleType vehicleType, int totalTicketCount, double price) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.vehicleType = vehicleType;
        this.totalTicketCount = totalTicketCount;
        this.soldTicketCount = 0;
        this.price = price;
        this.createdDate = LocalDateTime.now();
    }
}
