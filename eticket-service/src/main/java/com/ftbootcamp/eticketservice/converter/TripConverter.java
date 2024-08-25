package com.ftbootcamp.eticketservice.converter;

import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.TripGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.dto.response.TripStatisticsResponse;
import com.ftbootcamp.eticketservice.entity.Trip;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TripConverter {

    public static TripResponse toTripResponse(Trip trip) {
        return TripResponse.builder()
                .departureTime(trip.getDepartureTime())
                .arrivalTime(trip.getArrivalTime())
                .departureCity(trip.getDepartureCity())
                .arrivalCity(trip.getArrivalCity())
                .vehicleType(trip.getVehicleType())
                .totalTicketCount(trip.getTotalTicketCount())
                .soldTicketCount(trip.getSoldTicketCount())
                .price(trip.getPrice())
                .createdDate(trip.getCreatedDate())
                .isCancelled(trip.isCancelled())
                .build();
    }

    public static Trip toUpdatedTripEntity(Trip trip, TripUpdateRequest request) {
        if(request.getDepartureTime() != null) {
            trip.setDepartureTime(request.getDepartureTime());
        }
        if(request.getArrivalTime() != null) {
            trip.setArrivalTime(request.getArrivalTime());
        }
        if(request.getDepartureCity() != null) {
            trip.setDepartureCity(request.getDepartureCity());
        }
        if(request.getArrivalCity() != null) {
            trip.setArrivalCity(request.getArrivalCity());
        }
        if(request.getVehicleType() != null) {
            trip.setVehicleType(request.getVehicleType());
        }
        if(request.getTotalTicketCount() != 0) {
            trip.setTotalTicketCount(request.getTotalTicketCount());
        }
        if(request.getPrice() != 0) {
            trip.setPrice(request.getPrice());
        }
        return trip;
    }

    public static TripStatisticsResponse toTripStatisticsResponse(int totalTicketCount, int soldTicketCount,
                                                                  double totalSoldTicketPrice) {

        return TripStatisticsResponse.builder()
                .totalTicketCount(totalTicketCount)
                .soldTicketCount(soldTicketCount)
                .totalSoldTicketPrice(totalSoldTicketPrice)
                .build();
    }

    public static TripGeneralStatisticsResponse toGeneralTripStatisticsResponse(int totalTripCount,
                                                                                 int totalAvailableTripCount,
                                                                                 int totalExpiredNotCanceledTripCount,
                                                                                 int totalCancelledTripCount) {

          return TripGeneralStatisticsResponse.builder()
                 .totalTripCount(totalTripCount)
                 .totalAvailableTripCount(totalAvailableTripCount)
                 .totalExpiredNotCanceledTripCount(totalExpiredNotCanceledTripCount)
                 .totalCancelledTripCount(totalCancelledTripCount)
                 .build();
    }
}
