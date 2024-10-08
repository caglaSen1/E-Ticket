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
            throw new ETicketException(ExceptionMessages.TRIP_NOT_FOUND + "Id: " + id);
        }

        return tripRepository.findById(id).get();
    }

    public void checkIfThereAreAnySoldTickets(long tripId) {
        int soldTicketCount = checkTripExistById(tripId).getSoldTicketCount();

        if (soldTicketCount > 0) {
            throw new ETicketException(ExceptionMessages.SOLD_TICKETS_EXISTS_TRIP_CANNOT_UPDATED_CANCELED);
        }
    }

    public void checkArrivalTimeValid(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime)) {
            throw new ETicketException(ExceptionMessages.ARRIVAL_EARLIER_THAN_DEPARTURE +
                    " Arrival time: " + arrivalTime + ", Departure time: " + departureTime);
        }
    }

    public void checkTotalTicketCountValid(int totalTicketCount) {
        if (totalTicketCount <= 0) {
            throw new ETicketException(ExceptionMessages.TOTAL_TICKET_LESS_THAN_ZERO +
                    " Total ticket count: " + totalTicketCount);
        }
    }

    public void checkPriceValid(double price) {
        if (price <= 0) {
            throw new ETicketException(ExceptionMessages.PRICE_LESS_THAN_ZERO + " Price: " + price);
        }
    }
}
