package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.converter.TripConverter;
import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.TripGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.dto.response.TripStatisticsResponse;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import com.ftbootcamp.eticketservice.rules.TripBusinessRules;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

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

    public TripResponse getTripById(Long id) {
        return TripConverter.toTripResponse(tripBusinessRules.checkTripExistById(id));
    }

    public List<TripResponse> getAllAvailableTrips() {
        return tripRepository.findAllAvailableTrips().stream()
                .map(TripConverter::toTripResponse)
                .toList();
    }

    public List<TripResponse> getAllExpiredNotCanceledTrips(){
        return tripRepository.findExpiredTrips().stream()
                .map(TripConverter::toTripResponse)
                .toList();
    }

    public List<TripResponse> getAllCancelledTrips() {
        return tripRepository.findCancelledTrips().stream()
                .map(TripConverter::toTripResponse)
                .toList();
    }

    public TripGeneralStatisticsResponse getGeneralTripStatistics() {
        List<Trip> trip = tripRepository.findAll();

        int totalTripCount = trip.size();
        int totalAvailableTripCount = tripRepository.findAllAvailableTrips().size();
        int totalExpiredNotCanceledTripCount = tripRepository.findExpiredTrips().size();
        int totalCancelledTripCount = tripRepository.findCancelledTrips().size();

        return TripConverter.toGeneralTripStatisticsResponse(totalTripCount, totalAvailableTripCount,
                totalExpiredNotCanceledTripCount, totalCancelledTripCount);
    }

    public TripStatisticsResponse getTripStatistics(Long id) {
        Trip trip = tripBusinessRules.checkTripExistById(id);

        int totalTicketCount = trip.getTotalTicketCount();
        int soldTicketCount = trip.getSoldTicketCount();
        double totalSoldTicketPrice = soldTicketCount * trip.getPrice();

        return TripConverter.toTripStatisticsResponse(totalTicketCount, soldTicketCount, totalSoldTicketPrice);
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

    public void cancelTrip(Long id) {
        Trip tripToCancel = tripBusinessRules.checkTripExistById(id);
        tripBusinessRules.checkIfThereAreAnySoldTickets(id);

        ticketService.deleteTicketsByTripId(id);

        tripToCancel.setCancelled(true);
        tripRepository.save(tripToCancel);
    }
}
