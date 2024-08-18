package com.ftbootcamp.eticketservice.entity;

import com.ftbootcamp.eticketservice.entity.constant.TicketEntityConstants;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = TicketEntityConstants.SEAT_NUMBER, nullable = false)
    private int seatNumber;

    @Column(name = TicketEntityConstants.PASSENGER_ID)
    private long passengerId;

    @Column(name = TicketEntityConstants.PRICE)
    private final double price = trip.getPrice();

}
