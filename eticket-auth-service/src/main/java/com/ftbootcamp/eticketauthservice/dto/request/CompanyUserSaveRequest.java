package com.ftbootcamp.eticketauthservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyUserSaveRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String companyName;
    private Long taxNumber;
}
