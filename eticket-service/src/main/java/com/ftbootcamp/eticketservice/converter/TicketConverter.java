package com.ftbootcamp.eticketservice.converter;

import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TicketConverter {

    public static TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .trip(TripConverter.toTripResponse(ticket.getTrip()))
                .seatNo(ticket.getSeatNo())
                .price(ticket.getPrice())
                .passengerEmail(ticket.getPassengerEmail())
                .isTaken(ticket.isTaken())
                .build();
    }
}
