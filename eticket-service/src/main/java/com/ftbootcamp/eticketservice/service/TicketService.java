package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.user.UserClientService;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.converter.TicketConverter;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.producer.RabbitMqProducer;
import com.ftbootcamp.eticketservice.producer.dto.EmailSendRequest;
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
    private final RabbitMqProducer rabbitMqProducer;

    public TicketResponse buyTicket(TicketBuyRequest request) {
        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserById(request.getUserId());

        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());
        ticketBusinessRules.checkTicketSold(ticket);

        // TODO: Get payment

        ticket.setPassengerEmail(user.getEmail());
        ticket.setBought(true);
        ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

        ticketRepository.save(ticket);

        log.info("Ticket bought. ticket id: {}, user: {}", ticket.getId(), user.getEmail());

        // Send ticket info message with RabbitMQ Service (Asencronize):
        String infoMessage = generateTicketInfoMessage(user, ticket);
        rabbitMqProducer.sendTicketInfoMessage(new EmailSendRequest(user.getEmail(), infoMessage));

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

    private String generateTicketInfoMessage(UserDetailsResponse user, Ticket ticket) {
        return "Your ticket has been created successfully." + "\n" +
                "Your ticket information: " + "\n" +
                ticket.getTrip().getDepartureTime() + " - " + ticket.getTrip().getArrivalTime() + "\n" +
                ticket.getTrip().getDepartureCity() + " -> " + ticket.getTrip().getArrivalCity() + "\n" +
                "Ticket No: " + ticket.getId() + "\n" +
                "Seat No: " + ticket.getSeatNo() + "\n" +
                "Price: " + ticket.getPrice() + "\n" +
                "Passenger information: " + "\n" +
                "Name: " + "user.getFullName()" + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Phone number: " + "user.getPhone()";
    }
}
