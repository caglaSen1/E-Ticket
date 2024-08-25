package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividualUserSaveRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private long nationalId;
    private LocalDateTime birthDate;
    private Gender gender;
}
