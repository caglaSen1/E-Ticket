package com.ftbootcamp.eticketservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionMessages {

    // Trip
    public static final String TRIP_NOT_FOUND = "Trip not found";

    // Ticket
    public static final String TICKET_NOT_FOUND = "Ticket not found";
    public static final String SOLD_TICKETS_EXISTS_TRIP_CANNOT_UPDATED_CANCELED = "There are sold tickets for this trip."
            + " It cannot be updated or canceled until sold tickets are refunded.";
    public static final String ARRIVAL_EARLIER_THAN_DEPARTURE = "Arrival time cannot be earlier than departure time";
    public static final String TOTAL_TICKET_LESS_THAN_ZERO = "Total ticket count cannot be less than zero";
    public static final String PRICE_LESS_THAN_ZERO = "Price cannot be less than zero";
    public static final String TICKET_ALREADY_SOLD = "Ticket already sold";
    public static final String TICKET_EXPIRED = "Ticket expired";
    public static final String TICKET_NOT_AVAILABLE = "Ticket is not available";
}
