package com.ftbootcamp.eticketservice.client.user.dto;

import com.ftbootcamp.eticketservice.client.user.dto.RoleResponse;
import com.ftbootcamp.eticketservice.client.user.enums.Gender;
import com.ftbootcamp.eticketservice.client.user.enums.StatusType;
import com.ftbootcamp.eticketservice.client.user.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsResponse {

    private String email;

    private LocalDateTime birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    private List<RoleResponse> roles;

    private LocalDateTime createdDate;

}
