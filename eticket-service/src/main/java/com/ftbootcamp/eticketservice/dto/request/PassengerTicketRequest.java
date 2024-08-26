package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.user.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PassengerTicketRequest {

    @NotBlank(message = "Passenger first name is mandatory")
    @Size(max = 50, message = "Passenger first name must not exceed 50 characters")
    private String passengerFirstName;

    @NotBlank(message = "Passenger last name is mandatory")
    @Size(max = 50, message = "Passenger last name must not exceed 50 characters")
    private String passengerLastName;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @NotNull(message = "Ticket ID is mandatory")
    @Positive(message = "Ticket ID must be a positive number")
    private Long ticketId;
}
