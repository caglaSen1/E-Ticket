package com.ftbootcamp.eticketauthservice.client.user.dto;

import com.ftbootcamp.eticketauthservice.client.user.enums.Gender;
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
