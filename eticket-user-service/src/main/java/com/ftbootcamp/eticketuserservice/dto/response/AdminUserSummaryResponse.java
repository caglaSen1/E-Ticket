package com.ftbootcamp.eticketuserservice.dto.response;

import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserSummaryResponse {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDateTime createdDate;
}
