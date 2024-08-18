package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.repository.TicketRepository;
import com.ftbootcamp.eticketservice.rules.TicketBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketBusinessRules ticketBusinessRules;

    public TicketResponse buyTicket(TicketBuyRequest request) {

        // TODO: Get user with userClient, make controls
        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());

        // TODO: Set email
        ticket.setPassengerEmail("");
        ticket.setTaken(true);

        log.info("Ticket bought: {}, user: {}", ticket.getId(), request.getUserId());

        return null;
    }

    public void generateTicketsForTrip(Trip trip) {
        for (int i = 0; i < trip.getCapacity(); i++) {

            String seatNo = String.valueOf(i + 1);

            Ticket ticket = new Ticket(trip, seatNo);

            ticketRepository.save(ticket);
        }

    }
}
