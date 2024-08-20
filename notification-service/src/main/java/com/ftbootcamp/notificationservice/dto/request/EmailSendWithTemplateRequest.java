package com.ftbootcamp.notificationservice.dto.request;

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
