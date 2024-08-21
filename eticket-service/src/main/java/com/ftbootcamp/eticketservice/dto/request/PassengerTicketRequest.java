package com.ftbootcamp.eticketservice.dto.request;

import com.ftbootcamp.eticketservice.client.user.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PassengerTicketRequest {

    private String passengerFirstName;
    private String passengerLastName;
    private Gender gender;
    private long ticketId;
}
