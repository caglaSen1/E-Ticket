package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyUserSaveRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Company name is mandatory")
    private String companyName;

    @NotNull(message = "Tax number is mandatory")
    private Long taxNumber;
}
