package com.ftbootcamp.eticketuserservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateRequest {

    private String email;
    private String password;
}
