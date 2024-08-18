package com.ftbootcamp.eticketuserservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleRequest {
    private String email;
    private String roleName;

    public String getRoleName(){
        return roleName.toUpperCase();
    }
}
