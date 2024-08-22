package com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto;

import com.ftbootcamp.eticketuserservice.producer.rabbitmq.enums.NotificationType;
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
