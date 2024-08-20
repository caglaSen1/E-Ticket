package com.ftbootcamp.notificationservice.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailSendRequest {

    private String to;
    private String text;
}
