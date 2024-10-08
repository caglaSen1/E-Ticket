package com.ftbootcamp.eticketservice.entity;

import com.ftbootcamp.eticketservice.entity.constant.TicketEntityConstants;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
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
    private String seatNo;

    @Column(name = TicketEntityConstants.PRICE)
    private double price;

    @Column(name = TicketEntityConstants.PASSENGER_EMAIL)
    private String passengerEmail;

    @Column(name = TicketEntityConstants.IS_SOLD)
    private boolean isSold = false;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Ticket(Trip trip, String seatNo) {
        this.trip = trip;
        this.seatNo = seatNo;
        this.price = trip.getPrice();
        this.isSold = false;
    }
}
