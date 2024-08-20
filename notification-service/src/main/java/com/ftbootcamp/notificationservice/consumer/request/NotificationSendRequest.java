package com.ftbootcamp.notificationservice.consumer.request;

import com.ftbootcamp.notificationservice.consumer.enums.NotificationType;
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
