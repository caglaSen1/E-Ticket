package com.ftbootcamp.eticketuserservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleCreateRequest {
    private String name;
}
