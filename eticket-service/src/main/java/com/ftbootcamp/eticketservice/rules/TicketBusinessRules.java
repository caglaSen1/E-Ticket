package com.ftbootcamp.eticketservice.rules;

import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketBusinessRules {

    private final TicketRepository ticketRepository;

    public Ticket checkTicketExistById(Long id) {
        if (ticketRepository.findById(id).isEmpty()) {
            throw new ETicketException(ExceptionMessages.TICKET_NOT_FOUND + " Id: " + id);
        }

        return ticketRepository.findById(id).get();
    }

    public List<Ticket> checkTicketsExistByIdList(List<Long> ids) {

        List<Ticket> foundedTickets = new ArrayList<>();

        for (long id : ids) {
            if (ticketRepository.findById(id).isEmpty()) {
                throw new ETicketException(ExceptionMessages.TICKET_NOT_FOUND + " Id: " + id);
            }

            foundedTickets.add(ticketRepository.findById(id).get());
        }

        return foundedTickets;
    }

    public void checkTicketIsAvailable(Ticket ticket) {
        ticketRepository.findAllAvailableTickets().stream()
                .filter(t -> t.getId().equals(ticket.getId()))
                .findFirst()
                .orElseThrow(() -> new ETicketException(ExceptionMessages.TICKET_NOT_AVAILABLE +
                        " Id: " + ticket.getId()));
    }

    public void checkTicketListIsAvailable(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            checkTicketIsAvailable(ticket);
        }
    }
}
