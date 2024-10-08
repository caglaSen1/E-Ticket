package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.client.payment.dto.request.PaymentGenericRequest;
import com.ftbootcamp.eticketservice.client.payment.service.PaymentClientService;
import com.ftbootcamp.eticketservice.client.user.service.UserClientService;
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
import com.ftbootcamp.eticketservice.entity.enums.TicketCondition;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketservice.producer.Log;
import com.ftbootcamp.eticketservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketservice.producer.rabbitmq.dto.NotificationSendRequest;
import com.ftbootcamp.eticketservice.producer.rabbitmq.enums.NotificationType;
import com.ftbootcamp.eticketservice.repository.TicketRepository;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import com.ftbootcamp.eticketservice.rules.TicketBusinessRules;
import com.ftbootcamp.eticketservice.utils.ExtractFromToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ftbootcamp.eticketservice.utils.MessageUtils.generateMultipleTicketInfoMessage;
import static com.ftbootcamp.eticketservice.utils.MessageUtils.generateTicketInfoMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final TicketBusinessRules ticketBusinessRules;
    private final UserClientService userService;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;
    private final PaymentClientService paymentClientService;

    public void takePaymentOfTicket(TicketBuyRequest request, String token) {
        String email = ExtractFromToken.email(token);

        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserByEmail(email);

        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());
        ticketBusinessRules.checkTicketIsAvailable(ticket);

        // Get Payment of Ticket with Payment Service (sencronous):
        PaymentGenericRequest<TicketBuyRequest> paymentGenericRequest =
                new PaymentGenericRequest<>(request.getPaymentType(),
                        new BigDecimal(ticket.getPrice()), user.getEmail(), request,
                        "TicketBuyRequest");

        paymentClientService.takePayment(paymentGenericRequest);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Payment transaction send to payment-service for ticket. " +
                "Request: " + request));
    }

    public void saveTicketAfterPayment(TicketBuyRequest request, String userEmail) {
        // Get user with userClient (sencronous):
        UserDetailsResponse user = userService.getUserByEmail(userEmail);

        Ticket ticket = ticketBusinessRules.checkTicketExistById(request.getTicketId());
        ticketBusinessRules.checkTicketIsAvailable(ticket);

        ticket.setPassengerEmail(user.getEmail());
        ticket.setSold(true);
        ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

        tripRepository.save(ticket.getTrip());
        ticketRepository.save(ticket);

        // Generate ticket info message:
        String infoMessage = generateTicketInfoMessage(user, ticket);

        // Send ticket info message to buyer with RabbitMQ (Asencronize):
        sendTicketInfoMessage(user, infoMessage, List.of(NotificationType.EMAIL, NotificationType.SMS));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Ticket buying process completed. Buyer: " + user.getEmail() +
                " ticket id: " + ticket.getId()));

    }

    public void takePaymentOfMultipleTickets(TicketMultipleBuyRequest request, String token) {
        String email = ExtractFromToken.email(token);

        // Get user with userClient (sencronous):
        UserDetailsResponse buyer = userService.getUserByEmail(email);

        List<Ticket> tickets = ticketBusinessRules.checkTicketsExistByIdList(
                request.getPassengerTicketRequests()
                        .stream()
                        .map(PassengerTicketRequest::getTicketId)
                        .toList()
        );

        ticketBusinessRules.checkTicketListIsAvailable(tickets);

        // Control Project Requirements:
        controlProjectRequirements(buyer, tickets, request.getPassengerTicketRequests());

        // Get Payment of Tickets with Payment Service (Sencronize):
        double totalPrice = tickets.stream().mapToDouble(Ticket::getPrice).sum();

        PaymentGenericRequest<TicketMultipleBuyRequest> paymentGenericRequest =
                new PaymentGenericRequest<>(request.getPaymentType(),
                new BigDecimal(totalPrice), buyer.getEmail(), request,
                "TicketMultipleBuyRequest");

        paymentClientService.takePayment(paymentGenericRequest);

        kafkaProducer.sendLogMessage(new Log("Payment transaction send to payment-service for multiple tickets. " +
                "Buyer: " + buyer.getEmail() + " ticket ids: " + tickets.stream().map(Ticket::getId).toList()));
    }

    public void saveTicketsAfterPayment(TicketMultipleBuyRequest request, String userEmail) {

        UserDetailsResponse buyer = userService.getUserByEmail(userEmail);

        List<Ticket> tickets = ticketBusinessRules.checkTicketsExistByIdList(
                request.getPassengerTicketRequests().stream()
                        .map(PassengerTicketRequest::getTicketId)
                        .toList()
        );

        for (Ticket ticket : tickets) {
            ticket.setPassengerEmail(buyer.getEmail());
            ticket.setSold(true);
            ticket.getTrip().setSoldTicketCount(ticket.getTrip().getSoldTicketCount() + 1);

            tripRepository.save(ticket.getTrip());
            ticketRepository.save(ticket);
        }

        // Generate ticket info message:
        String infoMessage = generateMultipleTicketInfoMessage(buyer, tickets,
                request.getPassengerTicketRequests());

        // Send ticket info message to buyer with RabbitMQ (Asencronize):
        sendTicketInfoMessage(buyer, infoMessage, List.of(NotificationType.EMAIL, NotificationType.SMS));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Ticket buying process completed. Buyer: " + buyer.getEmail() +
                " ticket ids: " + tickets.stream().map(Ticket::getId).toList()));
    }

    public TicketResponse getTicketById(Long id) {;
        return TicketConverter.toTicketResponse(ticketBusinessRules.checkTicketExistById(id));
    }

    //@Cacheable(value = "tickets", cacheNames = "tickets")
    public List<TicketResponse> getAllTickets() {
        log.info("datadan alındı");
        return TicketConverter.toTicketResponseList(ticketRepository.findAll());
    }

    public List<TicketResponse> getAllTicketsOfBuyerByEmail(String email) {
        return TicketConverter.toTicketResponseList(ticketRepository.findAllByUserEmail(email));
    }

    public List<TicketResponse> getAllTicketsOfBuyer(String token) {
        String email = ExtractFromToken.email(token);
        return TicketConverter.toTicketResponseList(ticketRepository.findAllByUserEmail(email));
    }

    public List<TicketResponse> getAllByCondition(TicketCondition condition) {
        switch (condition) {
            case AVAILABLE:
                return TicketConverter.toTicketResponseList(ticketRepository.findAllAvailableTickets());
            case EXPIRED:
                return TicketConverter.toTicketResponseList(ticketRepository.findAllExpiredTickets());
            case SOLD:
                return TicketConverter.toTicketResponseList(ticketRepository.findAllSoldTickets());
            default:
                return TicketConverter.toTicketResponseList(ticketRepository.findAllAvailableTickets());
        }
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
            kafkaProducer.sendLogMessage(new Log("Ticket soft deleted. ticket id: " + ticket.getId()));
        });
    }

    // Redis
    //@CacheEvict(cacheNames = "tickets", allEntries = true)
    //@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackForClassName = {"ETicketException.class"},
            //rollbackFor = SQLException.class)
    public void generateTicketsForTrip(Trip trip) {
        for (int i = 1; i <= trip.getTotalTicketCount(); i++) {

            String seatNo = String.valueOf(i);

            Ticket ticket = new Ticket(trip, seatNo);

            ticketRepository.save(ticket);

            // Send log message with Kafka for saving in MongoDB (Asencronize):
            kafkaProducer.sendLogMessage(new Log("Ticket generated. ticket id: " + ticket.getId()));
        }
    }

    private void sendTicketInfoMessage(UserDetailsResponse user, String infoMessage,
                                       List<NotificationType> notificationTypes) {
        List<NotificationType> types = new ArrayList<>();
        if(notificationTypes.contains(NotificationType.EMAIL) && user.getEmail() != null){
            types.add(NotificationType.EMAIL);
        }else if (notificationTypes.contains(NotificationType.SMS) && user.getPhoneNumber() != null){
            types.add(NotificationType.SMS);
        } else if (notificationTypes.contains(NotificationType.PUSH)) {
            types.add(NotificationType.PUSH);
        }

        rabbitMqProducer.sendTicketInfoMessage(new NotificationSendRequest(notificationTypes, user.getEmail(),
                user.getPhoneNumber(), infoMessage));
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
                if (ticketList.size() > 5) {
                    throw new ETicketException("Exception: Individual users can buy at most 5 tickets for a trip.");
                }
            });

            if (malePassengerCountForOrder > 2) {
                throw new ETicketException("Exception: Individual users can buy tickets for up to 2 male " +
                        "passengers in a single order.");
            }
        }

        if (instanceOf.equals(RoleNameConstants.CORPORATE_USER_ROLE_NAME)) {
            tripTicketsMap.forEach((trip, ticketList) -> {
                if (ticketList.size() > 40) {
                    throw new ETicketException("Exception: Company users can buy at most 40 tickets for a trip.");
                }
            });
        }
    }
}