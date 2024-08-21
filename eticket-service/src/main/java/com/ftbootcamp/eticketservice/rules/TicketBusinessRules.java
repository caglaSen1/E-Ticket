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
            handleException(ExceptionMessages.TICKET_NOT_FOUND, "Id: " + id);
        }

        return ticketRepository.findById(id).get();
    }

    public List<Ticket> checkTicketsExistByIdList(List<Long> ids) {

        List<Ticket> foundedTickets = new ArrayList<>();

        for (long id : ids) {
            if (ticketRepository.findById(id).isEmpty()) {
                handleException(ExceptionMessages.TICKET_NOT_FOUND, "Id: " + id);
                return null;
            }

            foundedTickets.add(ticketRepository.findById(id).get());
        }

        return foundedTickets;
    }

    public void checkTicketSold(Ticket ticket) {
        if (ticket.isBought()) {
            handleException(ExceptionMessages.TICKET_ALREADY_SOLD, "Ticket: " + ticket.getId());
        }
    }

    public void checkTicketListSold(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.isBought()) {
                handleException(ExceptionMessages.TICKET_ALREADY_SOLD + " Ticket: " + ticket.getId(), "Ticket: " + ticket.getId());
            }
        }
    }

    private void handleException(String exceptionMessage, String request) {
        String logMessage;

        if (request != null && !request.isEmpty()) {
            logMessage = String.format("Log: Error: %s, Request: %s", exceptionMessage, request);
        } else {
            logMessage = String.format("Log: Error: %s", exceptionMessage);
        }

        throw new ETicketException(exceptionMessage, logMessage);
    }
}
