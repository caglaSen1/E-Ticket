package com.ftbootcamp.eticketservice.producer.dto;

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
