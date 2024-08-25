package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminUserSaveRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private long nationalId;
    private Gender gender;
}
