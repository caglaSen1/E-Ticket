package com.ftbootcamp.eticketsearchservice.converter;

import com.ftbootcamp.eticketsearchservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketsearchservice.model.document.TripDocument;
import com.ftbootcamp.eticketsearchservice.model.enums.VehicleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TripConverter {

    public static TripDocumentResponse toTripDocumentResponse(TripDocument tripDocument) {
        return TripDocumentResponse.builder()
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
