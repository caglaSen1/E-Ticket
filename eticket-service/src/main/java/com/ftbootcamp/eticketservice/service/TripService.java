package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.converter.TripConverter;
import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import com.ftbootcamp.eticketservice.rules.TripBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final TripBusinessRules tripBusinessRules;

    public TripResponse create(TripCreateRequest tripCreateRequest) {
        // TODO: Add business rules

        Trip trip = Trip.builder()
                .departureTime(tripCreateRequest.getDepartureTime())
                .departureCity(tripCreateRequest.getDepartureCity())
                .arrivalCity(tripCreateRequest.getArrivalCity())
                .vehicleType(tripCreateRequest.getVehicleType())
                .availableSeats(tripCreateRequest.getAvailableSeats())
                .price(tripCreateRequest.getPrice())
                .createdDate(LocalDateTime.now())
                .build();

        tripRepository.save(trip);

        return TripConverter.toTripResponse(trip);
    }

    // TODO: getAllAvailableTrips()

    public TripResponse getTripById(Long id) {
        // TODO: Add business rules

        return TripConverter.toTripResponse(tripBusinessRules.checkTripExistById(id));
    }

    public TripResponse updateTrip(TripUpdateRequest request) {
        Trip tripToUpdate = tripBusinessRules.checkTripExistById(request.getId());
        Trip updatedTrip = TripConverter.toUpdatedTripEntity(tripToUpdate, request);

        tripRepository.save(updatedTrip);

        return TripConverter.toTripResponse(updatedTrip);
    }

    public void deleteTrip(Long id) {
        tripBusinessRules.checkTripExistById(id);
        tripRepository.deleteById(id);
    }
}
