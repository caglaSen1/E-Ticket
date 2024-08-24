package com.ftbootcamp.eticketuserservice.dto.response.user;

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
public class UserSummaryResponse {

    private String email;
    private String phoneNumber;
    private LocalDateTime createdDate;
}
