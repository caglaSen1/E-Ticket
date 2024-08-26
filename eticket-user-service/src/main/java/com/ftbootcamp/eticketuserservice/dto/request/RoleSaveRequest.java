package com.ftbootcamp.eticketuserservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleSaveRequest {

    @NotBlank(message = "Role name is mandatory")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    private String name;
}
