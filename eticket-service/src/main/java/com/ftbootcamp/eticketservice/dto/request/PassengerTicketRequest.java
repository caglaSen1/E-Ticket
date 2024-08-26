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
    private String passengerFirstName;

    @NotBlank(message = "Passenger last name is mandatory")
    private String passengerLastName;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @NotNull(message = "Ticket ID is mandatory")
    private Long ticketId;
}
