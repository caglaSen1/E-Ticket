package com.ftbootcamp.emailservice.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailSendWithTemplateRequest {

    private String to;
    private String emailTemplateName;
}
