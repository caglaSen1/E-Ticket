package com.ftbootcamp.eticketauthservice.dto.response;

import com.ftbootcamp.eticketauthservice.client.user.dto.RoleResponse;
import com.ftbootcamp.eticketauthservice.client.user.enums.StatusType;
import com.ftbootcamp.eticketauthservice.client.user.enums.UserType;
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
