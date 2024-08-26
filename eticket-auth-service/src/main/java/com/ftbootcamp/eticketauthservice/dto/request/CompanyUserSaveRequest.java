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

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid and between 10 to 15 digits")
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @Size(min = PasswordConstants.PASSWORD_MIN_LENGTH, max = PasswordConstants.PASSWORD_MAX_LENGTH,
            message = "Password must be between " + PasswordConstants.PASSWORD_MIN_LENGTH + " and " +
                    PasswordConstants.PASSWORD_MAX_LENGTH + " characters")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Company name is mandatory")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String companyName;

    @NotNull(message = "Tax number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Tax number must be exactly 10 digits")
    private Long taxNumber;
}
