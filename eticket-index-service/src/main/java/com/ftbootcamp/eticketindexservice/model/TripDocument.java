package com.ftbootcamp.eticketindexservice.model;

import com.ftbootcamp.eticketindexservice.model.constants.TripDocumentConstants;
import com.ftbootcamp.eticketindexservice.model.enums.VehicleType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "trip")
public class TripDocument {

    @Id
    private String id;

    @Field(name = TripDocumentConstants.DEPARTURE_TIME, type = FieldType.Date)
    private Instant departureTime;

    @Field(name = TripDocumentConstants.ARRIVAL_TIME, type = FieldType.Date)
    private Instant arrivalTime;

    @Field(name = TripDocumentConstants.DEPARTURE_CITY)
    private String departureCity;

    @Field(name = TripDocumentConstants.ARRIVAL_CITY)
    private String arrivalCity;

    @Field(name = TripDocumentConstants.VEHICLE_TYPE)
    private String vehicleType;

    @Field(name = TripDocumentConstants.TOTAL_TICKET_COUNT)
    private int totalTicketCount;

    @Field(name = TripDocumentConstants.SOLD_TICKET_COUNT)
    private int soldTicketCount;

    @Field(name = TripDocumentConstants.PRICE)
    private double price;

    @Field(name = TripDocumentConstants.CREATED_DATE, type = FieldType.Date)
    private Instant createdDate;

    @Field(name = TripDocumentConstants.IS_CANCELLED)
    private boolean isCancelled = false;
}



