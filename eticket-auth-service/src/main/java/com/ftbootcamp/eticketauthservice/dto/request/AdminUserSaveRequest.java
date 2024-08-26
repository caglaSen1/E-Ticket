package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.entity.enums.Gender;
import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminUserSaveRequest {

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
    @Digits(integer = 11, fraction = 0, message = "National ID must be exactly 11 digits")
    private Long nationalId;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;
}
