package com.ftbootcamp.eticketgw.client.auth.dto;

import com.ftbootcamp.eticketgw.client.auth.enums.Gender;
import com.ftbootcamp.eticketgw.client.auth.enums.StatusType;
import com.ftbootcamp.eticketgw.client.auth.enums.UserType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsResponse {

    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private Gender gender;
    private LocalDateTime createdDate;
    private List<RoleResponse> roles;
    private String companyName;
    private Long taxNumber;
    private UserType userType;
    private StatusType statusType;
    private String instanceOf;
}
