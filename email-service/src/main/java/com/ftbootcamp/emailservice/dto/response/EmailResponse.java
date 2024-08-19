package com.ftbootcamp.emailservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailResponse {

    private String to;
    private String text;
    private LocalDateTime createdDateTime;
}
