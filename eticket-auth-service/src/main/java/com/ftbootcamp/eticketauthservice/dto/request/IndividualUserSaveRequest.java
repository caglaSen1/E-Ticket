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

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotNull(message = "National ID is mandatory")
    @Pattern(regexp = "^[0-9]{11}$", message = "National ID must be exactly 11 digits")
    private long nationalId;

    @NotNull(message = "Birth date is mandatory")
    private LocalDateTime birthDate;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;
}
