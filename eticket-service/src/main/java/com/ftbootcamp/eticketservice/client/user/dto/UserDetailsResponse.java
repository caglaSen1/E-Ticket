package com.ftbootcamp.eticketservice.client.user.dto;

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
    private String phoneNumber;
    private LocalDateTime createdDate;
    private List<RoleResponse> roles;

}
