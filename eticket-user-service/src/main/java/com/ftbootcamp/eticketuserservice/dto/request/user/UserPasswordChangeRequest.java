package com.ftbootcamp.eticketuserservice.dto.request.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPasswordChangeRequest {
    private String password;
}
