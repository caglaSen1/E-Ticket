package com.ftbootcamp.eticketuserservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleUpdateRequest {

    private Long id;
    private String name;
}
