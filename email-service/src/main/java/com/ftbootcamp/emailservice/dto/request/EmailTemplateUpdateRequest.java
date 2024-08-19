package com.ftbootcamp.emailservice.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailTemplateUpdateRequest {

    private String id;
    private String name;
    private String text;
}
