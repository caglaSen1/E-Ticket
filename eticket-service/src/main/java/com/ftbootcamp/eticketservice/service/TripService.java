package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.converter.TripConverter;
import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import com.ftbootcamp.eticketservice.rules.TripBusinessRules;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final TripBusinessRules tripBusinessRules;
    private final TicketService ticketService;

    // TODO: +cancelTrip()

    public TripResponse create(TripCreateRequest request) {
        tripBusinessRules.checkArrivalTimeValid(request.getDepartureTime(), request.getArrivalTime());
        tripBusinessRules.checkTotalTicketCountValid(request.getTotalTicketCount());
        tripBusinessRules.checkPriceValid(request.getPrice());

        Trip trip = new Trip(
                request.getDepartureTime(),
                request.getArrivalTime(),
                request.getDepartureCity(),
                request.getArrivalCity(),
                request.getVehicleType(),
                request.getTotalTicketCount(),
                request.getPrice()
        );

        tripRepository.save(trip);

        // Generate tickets for the trip
        ticketService.generateTicketsForTrip(trip);

        return TripConverter.toTripResponse(trip);
    }

    // TODO: getAllAvailableTrips()

    public TripResponse getTripById(Long id) {
        return TripConverter.toTripResponse(tripBusinessRules.checkTripExistById(id));
    }

    public TripResponse updateTrip(TripUpdateRequest request) {
        Trip tripToUpdate = tripBusinessRules.checkTripExistById(request.getId());

        tripBusinessRules.checkIfThereAreAnySoldTickets(tripToUpdate.getId());
        tripBusinessRules.checkArrivalTimeValid(request.getDepartureTime(), request.getArrivalTime());
        tripBusinessRules.checkTotalTicketCountValid(request.getTotalTicketCount());
        tripBusinessRules.checkPriceValid(request.getPrice());

        Trip updatedTrip = TripConverter.toUpdatedTripEntity(tripToUpdate, request);

        tripRepository.save(updatedTrip);

        return TripConverter.toTripResponse(updatedTrip);
    }

    @Transactional
    public void deleteTrip(Long id) {
        tripBusinessRules.checkTripExistById(id);
        tripBusinessRules.checkIfThereAreAnySoldTickets(id);

        ticketService.deleteTicketsByTripId(id);

        tripRepository.deleteById(id);
    }
}
