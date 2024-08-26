package com.ftbootcamp.eticketauthservice.dto.request;

import com.ftbootcamp.eticketauthservice.rules.constants.PasswordConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = PasswordConstants.PASSWORD_MIN_LENGTH, max = PasswordConstants.PASSWORD_MAX_LENGTH,
            message = "Password must be between " + PasswordConstants.PASSWORD_MIN_LENGTH + " and " +
                    PasswordConstants.PASSWORD_MAX_LENGTH + " characters")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
