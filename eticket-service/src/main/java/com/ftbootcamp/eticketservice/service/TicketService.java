package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.user.UserClientService;
import com.ftbootcamp.eticketservice.client.user.constants.RoleNameConstants;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.client.user.enums.Gender;
import com.ftbootcamp.eticketservice.converter.TicketConverter;
import com.ftbootcamp.eticketservice.dto.request.PassengerTicketRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketBuyRequest;
import com.ftbootcamp.eticketservice.dto.request.TicketMultipleBuyRequest;
import com.ftbootcamp.eticketservice.dto.response.TicketGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TicketResponse;
import com.ftbootcamp.eticketservice.entity.Ticket;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.producer.entity.LogMessage;
import com.ftbootcamp.eticketservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketservice.producer.rabbitmq.dto.NotificationSendRequest;
import com.ftbootcamp.eticketservice.producer.rabbitmq.enums.NotificationType;
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
    private final KafkaProducer kafkaProducer;

    public TicketResponse buyTicket(TicketBuyRequest request) {
        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserById(request.getUserId());

        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());
        ticketBusinessRules.checkTicketIsAvailable(ticket);

        // TODO: Get payment

        ticket.setPassengerEmail(user.getEmail());
        ticket.setSold(true);
        ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

        ticketRepository.save(ticket);

        // Send ticket info message with RabbitMQ (Asencronize):
        String infoMessage = generateTicketInfoMessage(user, ticket);
        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(NotificationType.EMAIL, user.getEmail(),
                infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new LogMessage("Ticket bought. ticket id: " + ticket.getId() + ", Buyer: " +
                user.getEmail()));

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

        ticketBusinessRules.checkTicketListIsAvailable(tickets);

        // Control Project Requirements:
        controlProjectRequirements(buyer, tickets, request.getPassengerTicketRequests());

        // TODO: Get payment

        for (Ticket ticket : tickets) {
            ticket.setPassengerEmail(buyer.getEmail());
            ticket.setSold(true);
            ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

            ticketRepository.save(ticket);
        }

        // Send ticket info message with RabbitMQ Service (Asencronize):
        String infoMessage = generateMultipleTicketInfoMessage(buyer, tickets, request.getPassengerTicketRequests());
        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(NotificationType.EMAIL, buyer.getEmail(),
                infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new LogMessage("Ticket bought. Buyer: " + buyer.getEmail() +
                "ticket ids: " + tickets.stream().map(Ticket::getId).toList()));

        return TicketConverter.toTicketResponseList(tickets);
    }

    public TicketResponse getTicketById(Long id) {;
        return TicketConverter.toTicketResponse(ticketBusinessRules.checkTicketExistById(id));
    }

    public List<TicketResponse> getAllTickets() {
        return TicketConverter.toTicketResponseList(ticketRepository.findAll());
    }

    public List<TicketResponse> getAllAvailableTickets() {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllAvailableTickets());
    }

    public List<TicketResponse> getAllExpiredTickets() {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllExpiredTickets());
    }

    public List<TicketResponse> getAllSoldTickets() {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllSoldTickets());
    }

    public List<TicketResponse> getAllTicketsByTripId(Long tripId) {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllByTripId(tripId));
    }

    public List<TicketResponse> getAllAvailableTicketsByTripId(Long tripId) {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllAvailableTicketsByTripId(tripId));
    }

    public TicketGeneralStatisticsResponse getGeneralTicketStatistics() {
        int totalTicketCount = ticketRepository.findAll().size();
        int totalAvailableTicketCount = ticketRepository.findAllAvailableTickets().size();
        int totalExpiredTicketCount = ticketRepository.findAllExpiredTickets().size();
        int totalSoldTicketCount = ticketRepository.findAll().stream().filter(Ticket::isSold).toList().size();
        double totalSoldTicketPrice = ticketRepository.findAll().stream()
                .filter(Ticket::isSold)
                .mapToDouble(Ticket::getPrice)
                .sum();

        return TicketConverter.toTicketGeneralStatisticsResponse(totalTicketCount, totalAvailableTicketCount,
                totalExpiredTicketCount, totalSoldTicketCount, totalSoldTicketPrice);
    }

    // TODO: returnTicket()

    public void deleteTicketsByTripId(Long tripId) {
        ticketRepository.findAllByTripId(tripId).forEach(ticket -> {
            ticket.setDeleted(true);
            ticketRepository.save(ticket);

            // Send log message with Kafka for saving in MongoDB (Asencronize):
            kafkaProducer.sendLogMessage(new LogMessage("Ticket soft deleted. ticket id: " + ticket.getId()));
        });
    }

    public void generateTicketsForTrip(Trip trip) {
        for (int i = 1; i <= trip.getTotalTicketCount(); i++) {

            String seatNo = String.valueOf(i);

            Ticket ticket = new Ticket(trip, seatNo);

            ticketRepository.save(ticket);

            // Send log message with Kafka for saving in MongoDB (Asencronize):
            kafkaProducer.sendLogMessage(new LogMessage("Ticket generated. ticket id: " + ticket.getId()));
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