package com.ftbootcamp.eticketuserservice.dto.response.individual;

import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IndividualUserSummaryResponse {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private Gender gender;
    private UserType userType;
    private StatusType statusType;
    private LocalDateTime createdDate;
}
