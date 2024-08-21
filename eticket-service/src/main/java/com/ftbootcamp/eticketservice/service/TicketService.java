package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.user.UserClientService;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.converter.TicketConverter;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.producer.RabbitMqProducer;
import com.ftbootcamp.eticketservice.producer.dto.NotificationSendRequest;
import com.ftbootcamp.eticketservice.producer.enums.NotificationType;
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
        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(NotificationType.EMAIL, user.getEmail(),
                infoMessage));

        return TicketConverter.toTicketResponse(ticket);
    }

    public List<TicketResponse> buyMultipleTicket(TicketMultipleBuyRequest request) {
        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserById(request.getUserId());

        // TODO: ADD project requirements

        List<Ticket> tickets = ticketBusinessRules.checkTicketsExistByIdList(request.getTicketIds());
        ticketBusinessRules.checkTicketListSold(tickets);

        // TODO: Get payment

        for (Ticket ticket : tickets) {
            ticket.setPassengerEmail(user.getEmail());
            ticket.setBought(true);
            ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

            ticketRepository.save(ticket);

            log.info("Ticket bought. ticket id: {}, user: {}", ticket.getId(), user.getEmail());
        }

        // Send ticket info message with RabbitMQ Service (Asencronize):
        String infoMessage = generateMultipleTicketInfoMessage(user, tickets);
        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(NotificationType.EMAIL, user.getEmail(),
                infoMessage));

        return TicketConverter.toTicketResponseList(tickets);
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
        StringBuilder infoMessage = new StringBuilder();

        infoMessage.append("************ Your ticket has been created successfully. ************\n");
        infoMessage.append("Buyer information:\n");
        //infoMessage.append("Name: ").append(user.getFullName()).append("\n");
        infoMessage.append("Email: ").append(user.getEmail()).append("\n");
        if(user.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(user.getPhoneNumber()).append("\n");
        }

        infoMessage.append("************************************\n");

        infoMessage.append("Your ticket information:\n");
        infoMessage.append(ticket.getTrip().getDepartureTime()).append(" - ")
                .append(ticket.getTrip().getArrivalTime()).append("\n");
        infoMessage.append(ticket.getTrip().getDepartureCity()).append(" -> ")
                .append(ticket.getTrip().getArrivalCity()).append("\n");
        infoMessage.append("Ticket No: ").append(ticket.getId()).append("\n");
        infoMessage.append("Seat No: ").append(ticket.getSeatNo()).append("\n");
        infoMessage.append("Price: ").append(ticket.getPrice()).append("\n");
        infoMessage.append("************************************\n");

        return infoMessage.toString();
    }

    private String generateMultipleTicketInfoMessage(UserDetailsResponse user, List<Ticket> tickets) {
        StringBuilder infoMessage = new StringBuilder();
        double totalPrice = 0;

        infoMessage.append("Your tickets have been created successfully.\n");
        infoMessage.append("Buyer information:\n");
        infoMessage.append("Email: ").append(user.getEmail()).append("\n");

        if(user.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(user.getPhoneNumber()).append("\n");
        }

        infoMessage.append("Your tickets information:\n");

        for(int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            totalPrice += ticket.getPrice();

            infoMessage.append("************ Ticket ").append(i+1).append(" ************\n");
            infoMessage.append(ticket.getTrip().getDepartureTime()).append(" - ")
                    .append(ticket.getTrip().getArrivalTime()).append("\n");
            infoMessage.append(ticket.getTrip().getDepartureCity()).append(" -> ")
                    .append(ticket.getTrip().getArrivalCity()).append("\n");
            infoMessage.append("Ticket No: ").append(ticket.getId()).append("\n");
            infoMessage.append("Seat No: ").append(ticket.getSeatNo()).append("\n");
            infoMessage.append("Price: ").append(ticket.getPrice()).append("\n");
            infoMessage.append("************************************\n");
        }

        infoMessage.append("Total Price: ").append(totalPrice).append("\n");

        return infoMessage.toString();

    }
}
