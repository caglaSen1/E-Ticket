package com.ftbootcamp.eticketuserservice.dto.response;

import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompanyUserSummaryResponse {

    private String email;
    private String phoneNumber;
    private String companyName;
    private Long taxNumber;
    private UserType userType;
    private StatusType statusType;
    private LocalDateTime createdDate;
}
