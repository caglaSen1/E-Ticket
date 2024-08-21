package com.ftbootcamp.eticketindexservice.converter;

import com.ftbootcamp.eticketindexservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketindexservice.model.Trip;
import com.ftbootcamp.eticketindexservice.model.TripDocument;
import com.ftbootcamp.eticketindexservice.model.enums.VehicleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TripConverter {

    public static TripDocument toTripDocument(Trip trip) {
        return TripDocument.builder()
                .id(String.valueOf(trip.getId()))
                .departureTime(trip.getDepartureTime().atZone(ZoneOffset.UTC).toInstant())
                .arrivalTime(trip.getArrivalTime().atZone(ZoneOffset.UTC).toInstant())
                .departureCity(trip.getDepartureCity())
                .arrivalCity(trip.getArrivalCity())
                .vehicleType(trip.getVehicleType().name())
                .totalTicketCount(trip.getTotalTicketCount())
                .soldTicketCount(trip.getSoldTicketCount())
                .price(trip.getPrice())
                .createdDate(trip.getCreatedDate().atZone(ZoneOffset.UTC).toInstant())
                .isCancelled(trip.isCancelled())
                .build();
    }

    public static List<TripDocumentResponse> toTripDocumentResponseList(List<TripDocument> tripDocuments) {
        return tripDocuments.stream()
                .map(tripDocument -> TripDocumentResponse.builder()
                        .departureTime(LocalDateTime.ofInstant(tripDocument.getDepartureTime(), ZoneOffset.UTC))
                        .arrivalTime(LocalDateTime.ofInstant(tripDocument.getArrivalTime(), ZoneOffset.UTC))
                        .departureCity(tripDocument.getDepartureCity())
                        .arrivalCity(tripDocument.getArrivalCity())
                        .vehicleType(VehicleType.valueOf(tripDocument.getVehicleType()))
                        .totalTicketCount(tripDocument.getTotalTicketCount())
                        .soldTicketCount(tripDocument.getSoldTicketCount())
                        .price(tripDocument.getPrice())
                        .createdDate(LocalDateTime.ofInstant(tripDocument.getCreatedDate(), ZoneOffset.UTC))
                        .isCancelled(tripDocument.isCancelled())
                        .build())
                .toList();
    }
}