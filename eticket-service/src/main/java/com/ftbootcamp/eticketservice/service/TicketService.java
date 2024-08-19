package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.user.UserClientService;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.converter.TicketConverter;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.repository.TicketRepository;
import com.ftbootcamp.eticketservice.rules.TicketBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketBusinessRules ticketBusinessRules;
    private final UserClientService userService;

    public TicketResponse buyTicket(TicketBuyRequest request) {
        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserById(request.getUserId());
        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());
        ticketBusinessRules.checkTicketSold(ticket);

        ticket.setPassengerEmail(user.getEmail());
        ticket.setBought(true);
        ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

        ticketRepository.save(ticket);

        log.info("Ticket bought. ticket id: {}, user: {}", ticket.getId(), user.getEmail());
        // TODO: send email to user

        return TicketConverter.toTicketResponse(ticket);
    }

    public TicketResponse getTicketById(Long id) {
        return TicketConverter.toTicketResponse(ticketBusinessRules.checkTicketExistById(id));
    }

    public List<TicketResponse> getAllTickets(Long tripId) {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllByTripId(tripId));
    }

    public List<TicketResponse> getNotSoldTickets(Long tripId) {
        return TicketConverter.toTicketResponseList(ticketRepository.findNotSoldByTripId(tripId));
    }

    // TODO: getAllTicketsCanBought() -> isBought = false, returnTicket(), delete

    public void deleteTicketsByTripId(Long tripId) {
        ticketRepository.deleteAllByTripId(tripId);
    }

    public void generateTicketsForTrip(Trip trip) {
        for (int i = 1; i <= trip.getTotalTicketCount(); i++) {

            String seatNo = String.valueOf(i);

            Ticket ticket = new Ticket(trip, seatNo);

            ticketRepository.save(ticket);
        }
    }
}
