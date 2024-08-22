package com.ftbootcamp.eticketsearchservice.converter;

import com.ftbootcamp.eticketsearchservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketsearchservice.dto.response.TripSearchResponse;
import com.ftbootcamp.eticketsearchservice.model.document.TripDocument;
import com.ftbootcamp.eticketsearchservice.model.enums.VehicleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
        List<TripDocumentResponse> tripDocumentResponses = new ArrayList<>();
        tripDocuments.forEach(tripDocument -> {
            tripDocumentResponses.add(toTripDocumentResponse(tripDocument));
        });

        return tripDocumentResponses;
    }

    public static TripSearchResponse toTripSearchResponse(Page<TripDocument> trips) {
        return TripSearchResponse.builder()
                .tripDocumentResponses(toTripDocumentResponseList(trips.getContent()))
                .totalPage(trips.getTotalPages())
                .totalElement(trips.getTotalElements())
                .build();
    }
}
