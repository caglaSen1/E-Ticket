package com.ftbootcamp.eticketuserservice.dto.response.admin;

import com.ftbootcamp.eticketuserservice.dto.response.role.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
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
