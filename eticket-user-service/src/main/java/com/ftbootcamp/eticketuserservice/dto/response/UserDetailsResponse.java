package com.ftbootcamp.eticketuserservice.dto.response;

import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
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
    private String phoneNumber;
    private LocalDateTime createdDate;
    private List<RoleResponse> roles;


}
