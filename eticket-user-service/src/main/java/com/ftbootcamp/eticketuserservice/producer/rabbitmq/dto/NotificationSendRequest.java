package com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto;

import com.ftbootcamp.eticketuserservice.producer.rabbitmq.enums.NotificationType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationSendRequest {

    private List<NotificationType> notificationTypes;
    private String email;
    private String phoneNumber;
    private String text;
}
