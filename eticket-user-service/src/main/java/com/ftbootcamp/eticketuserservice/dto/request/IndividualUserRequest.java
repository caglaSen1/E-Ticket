package com.ftbootcamp.eticketuserservice.dto.request;

import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividualUserRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private long nationalId;
    private LocalDateTime birthDate;
    private Gender gender;

}
