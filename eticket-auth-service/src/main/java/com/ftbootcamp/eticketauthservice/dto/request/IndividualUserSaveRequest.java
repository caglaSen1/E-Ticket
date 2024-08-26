package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividualUserSaveRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "National ID is mandatory")
    private long nationalId;

    @NotNull(message = "Birth date is mandatory")
    private LocalDateTime birthDate;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;
}
