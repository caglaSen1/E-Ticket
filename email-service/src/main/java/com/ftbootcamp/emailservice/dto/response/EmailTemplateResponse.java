package com.ftbootcamp.emailservice.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailTemplateResponse {

    private String name;
    private String text;
    private LocalDateTime createdDateTime;
}
