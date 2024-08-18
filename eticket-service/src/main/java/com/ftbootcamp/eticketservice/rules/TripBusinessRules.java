package com.ftbootcamp.eticketservice.rules;

import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
