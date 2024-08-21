package com.ftbootcamp.eticketuserservice.dto.request;

import com.ftbootcamp.eticketuserservice.entity.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminUserCreateRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private long nationalId;
    private Gender gender;
}
