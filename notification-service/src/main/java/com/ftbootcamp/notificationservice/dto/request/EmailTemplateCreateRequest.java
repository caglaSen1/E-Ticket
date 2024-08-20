package com.ftbootcamp.notificationservice.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailTemplateCreateRequest {

    private String name;
    private String text;
}
