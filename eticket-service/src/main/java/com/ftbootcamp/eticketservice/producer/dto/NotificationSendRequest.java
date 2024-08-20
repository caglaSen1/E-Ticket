package com.ftbootcamp.eticketservice.producer.dto;

import com.ftbootcamp.eticketservice.producer.enums.NotificationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationSendRequest {

    private NotificationType notificationType;
    private String to;
    private String text;
}
