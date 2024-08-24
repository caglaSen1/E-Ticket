package com.ftbootcamp.eticketauthservice.dto.response;

import com.ftbootcamp.eticketauthservice.client.user.dto.RoleResponse;
import com.ftbootcamp.eticketauthservice.client.user.enums.Gender;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserDetailsResponse {

    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDateTime createdDate;
    private List<RoleResponse> roles;
}
