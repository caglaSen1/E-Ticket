package com.ftbootcamp.eticketservice.rules;

import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TripBusinessRules {

    private final TripRepository tripRepository;

    public Trip checkTripExistById(Long id) {
        if (tripRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.TRIP_NOT_FOUND, "Id: " + id);
        }

        return tripRepository.findById(id).get();
    }

    public void checkIfThereAreAnySoldTickets(long tripId) {
        int soldTicketCount = checkTripExistById(tripId).getSoldTicketCount();

        if(soldTicketCount > 0){
            handleException(ExceptionMessages.SOLD_TICKETS_EXISTS_TRIP_CANNOT_UPDATED_CANCELED, "");
        }
    }

    public void checkArrivalTimeValid(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime)) {
            handleException(ExceptionMessages.ARRIVAL_EARLIER_THAN_DEPARTURE,
                    "Arrival time: " + arrivalTime + ", Departure time: " + departureTime);
        }
    }

    public void checkTotalTicketCountValid(int totalTicketCount) {
        if (totalTicketCount <= 0) {
            handleException(ExceptionMessages.TOTAL_TICKET_LESS_THAN_ZERO,
                    "Total ticket count: " + totalTicketCount);
        }
    }

    public void checkPriceValid(double price) {
        if (price <= 0) {
            handleException(ExceptionMessages.PRICE_LESS_THAN_ZERO, "Price: " + price);
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
