package com.ftbootcamp.eticketservice.converter;

import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TicketConverter {

    public static TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .trip(TripConverter.toTripResponse(ticket.getTrip()))
                .seatNo(ticket.getSeatNo())
                .price(ticket.getPrice())
                .passengerEmail(ticket.getPassengerEmail())
                .isBought(ticket.isBought())
                .build();
    }

    public static List<TicketResponse> toTicketResponseList(List<Ticket> tickets) {
        return tickets.stream()
                .map(TicketConverter::toTicketResponse)
                .toList();
    }
}
