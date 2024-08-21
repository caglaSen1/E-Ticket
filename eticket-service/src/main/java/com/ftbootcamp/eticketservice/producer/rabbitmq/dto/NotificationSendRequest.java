package com.ftbootcamp.eticketservice.producer.rabbitmq.dto;

import com.ftbootcamp.eticketservice.producer.rabbitmq.enums.NotificationType;
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
