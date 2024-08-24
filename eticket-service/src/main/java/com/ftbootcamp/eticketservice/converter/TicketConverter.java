package com.ftbootcamp.eticketservice.converter;

import com.ftbootcamp.eticketservice.dto.response.TicketGeneralStatisticsResponse;
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
                .isBought(ticket.isSold())
                .build();
    }

    public static List<TicketResponse> toTicketResponseList(List<Ticket> tickets) {
        return tickets.stream()
                .map(TicketConverter::toTicketResponse)
                .toList();
    }

    public static TicketGeneralStatisticsResponse toTicketGeneralStatisticsResponse(int totalTicketCount,
                                                                                     int totalAvailableTicketCount,
                                                                                     int totalExpiredTicketCount,
                                                                                     int totalSoldTicketCount,
                                                                                     double totalSoldTicketPrice) {
        return TicketGeneralStatisticsResponse.builder()
                .totalTicketCount(totalTicketCount)
                .totalAvailableTicketCount(totalAvailableTicketCount)
                .totalExpiredTicketCount(totalExpiredTicketCount)
                .totalSoldTicketCount(totalSoldTicketCount)
                .totalSoldTicketPrice(totalSoldTicketPrice)
                .build();
    }
}
