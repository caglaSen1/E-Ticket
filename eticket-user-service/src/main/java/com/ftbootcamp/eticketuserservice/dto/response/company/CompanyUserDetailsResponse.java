package com.ftbootcamp.eticketuserservice.dto.response.company;

import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompanyUserDetailsResponse {

    private String email;
    private String phoneNumber;
    private String companyName;
    private Long taxNumber;
    private UserType userType;
    private StatusType statusType;
    private LocalDateTime createdDate;
    private List<RoleResponse> roles;
}
