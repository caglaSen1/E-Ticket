package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.user.UserClientService;
import com.ftbootcamp.eticketservice.client.user.constants.RoleNameConstants;
import com.ftbootcamp.eticketservice.client.user.dto.RoleResponse;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.client.user.enums.Gender;
import com.ftbootcamp.eticketservice.converter.TicketConverter;
import com.ftbootcamp.eticketservice.dto.request.PassengerTicketRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.producer.RabbitMqProducer;
import com.ftbootcamp.eticketservice.producer.dto.NotificationSendRequest;
import com.ftbootcamp.eticketservice.producer.enums.NotificationType;
import com.ftbootcamp.eticketservice.repository.TicketRepository;
import com.ftbootcamp.eticketservice.rules.TicketBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        UserDetailsResponse buyer = userService.getUserById(request.getBuyerId());

        List<Ticket> tickets = ticketBusinessRules.checkTicketsExistByIdList(
                request.getPassengerTicketRequests()
                        .stream()
                        .map(PassengerTicketRequest::getTicketId)
                        .toList()
        );

        ticketBusinessRules.checkTicketListSold(tickets);

        // Control Project Requirements:
        controlProjectRequirements(buyer, tickets, request.getPassengerTicketRequests());

        // TODO: Get payment

        for (Ticket ticket : tickets) {
            ticket.setPassengerEmail(buyer.getEmail());
            ticket.setBought(true);
            ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

            ticketRepository.save(ticket);

            log.info("Ticket bought. ticket id: {}, user: {}", ticket.getId(), buyer.getEmail());
        }

        // Send ticket info message with RabbitMQ Service (Asencronize):
        String infoMessage = generateMultipleTicketInfoMessage(buyer, tickets, request.getPassengerTicketRequests());
        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(NotificationType.EMAIL, buyer.getEmail(),
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
        String instanceOf = user.getInstanceOf();

        infoMessage.append("************ Your ticket has been created successfully. ************\n");

        infoMessage.append("Buyer information:\n");

        infoMessage.append("Email: ").append(user.getEmail()).append("\n");
        if (user.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(user.getPhoneNumber()).append("\n");
        }
        if (instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)) {
            infoMessage.append("Company Name: ").append(user.getCompanyName()).append("\n");
        }
        if (instanceOf.equals(RoleNameConstants.INDIVIDUAL_USER_ROLE_NAME)) {
            infoMessage.append("Name: ").append(user.getFirstName()).append(" ")
                    .append(user.getLastName()).append("\n");
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

    private String generateMultipleTicketInfoMessage(UserDetailsResponse buyer, List<Ticket> tickets,
                                                     List<PassengerTicketRequest> passengerTicketRequests) {

        StringBuilder infoMessage = new StringBuilder();
        String instanceOf = buyer.getInstanceOf();
        double totalPrice = 0;

        infoMessage.append("Your tickets have been created successfully.\n");
        infoMessage.append("Buyer information:\n");
        infoMessage.append("Email: ").append(buyer.getEmail()).append("\n");

        if (buyer.getPhoneNumber() != null) {
            infoMessage.append("Phone number: ").append(buyer.getPhoneNumber()).append("\n");
        }

        if(instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)){
            infoMessage.append("Company Name: ").append(buyer.getCompanyName()).append("\n");
        }

        if(instanceOf.equals(RoleNameConstants.INDIVIDUAL_USER_ROLE_NAME)){
            infoMessage.append("Name: ").append(buyer.getFirstName()).append(" ")
                    .append(buyer.getLastName()).append("\n");
        }

        infoMessage.append("Your tickets information:\n");

        for (int i = 0; i < passengerTicketRequests.size(); i++) {
            Ticket ticket = ticketRepository.findById(passengerTicketRequests.get(i).getTicketId()).get();
            totalPrice += ticket.getPrice();

            infoMessage.append("************ Ticket ").append(i + 1).append(" ************\n");
            infoMessage.append(ticket.getTrip().getDepartureTime()).append(" - ")
                    .append(ticket.getTrip().getArrivalTime()).append("\n");
            infoMessage.append(ticket.getTrip().getDepartureCity()).append(" -> ")
                    .append(ticket.getTrip().getArrivalCity()).append("\n");
            infoMessage.append("Ticket No: ").append(ticket.getId()).append("\n");
            infoMessage.append("Seat No: ").append(ticket.getSeatNo()).append("\n");
            infoMessage.append("Price: ").append(ticket.getPrice()).append("\n");

            infoMessage.append("Passenger information:\n");
            infoMessage.append("Name: ").append(passengerTicketRequests.get(i).getPassengerFirstName()).append(" ")
                    .append(passengerTicketRequests.get(i).getPassengerLastName()).append("\n");
            infoMessage.append("Gender: ").append(passengerTicketRequests.get(i).getGender()).append("\n");

            infoMessage.append("************************************\n");
        }

        infoMessage.append("Total Price: ").append(totalPrice).append("\n");

        return infoMessage.toString();

    }

    private void controlProjectRequirements(UserDetailsResponse buyer, List<Ticket> tickets,
                                            List<PassengerTicketRequest> passengerTicketRequests) {

        String instanceOf = buyer.getInstanceOf();

        Map<Trip, List<Ticket>> tripTicketsMap = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getTrip));

        int malePassengerCountForOrder = passengerTicketRequests.stream()
                .filter(passengerTicketRequest -> passengerTicketRequest.getGender().equals(Gender.MALE))
                .toList()
                .size();

        if (instanceOf.equals(RoleNameConstants.INDIVIDUAL_USER_ROLE_NAME)) {
            tripTicketsMap.forEach((trip, ticketList) -> {
                if (ticketList.size() > 2) {
                    throw new ETicketException("Individual users can buy at most 5 tickets for a trip.",
                            "Log: Error: Individual users can buy at most 5 tickets for a trip.");
                }
            });

            if (malePassengerCountForOrder > 1) {
                throw new ETicketException("Individual users can buy tickets for up to 2 male passengers in a " +
                        "single order.", "Log: Error: Individual users can buy tickets for up to 2 male passengers in a"
                        + " single order.");
            }
        }

        if (instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)) {
            tripTicketsMap.forEach((trip, ticketList) -> {
                if (ticketList.size() > 2) {
                    throw new ETicketException("Company users can buy at most 40 tickets for a trip.", "Log: Error: " +
                            "Company users can buy at most 40 tickets for a trip.");
                }
            });
        }
    }
}