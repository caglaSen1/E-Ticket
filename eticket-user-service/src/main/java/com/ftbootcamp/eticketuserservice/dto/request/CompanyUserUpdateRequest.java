package com.ftbootcamp.eticketuserservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyUserUpdateRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String companyName;
    private Long taxNumber;
}
