package com.ftbootcamp.notificationservice.consumer;

import com.ftbootcamp.notificationservice.consumer.strategy.*;
import com.ftbootcamp.notificationservice.service.EmailService;
import com.ftbootcamp.notificationservice.consumer.enums.NotificationType;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final EmailService emailService;
    private final NotificationContext notificationContext;

    @RabbitListener(queuesToDeclare = @Queue("${rabbitmq.send.notification.queue}"))
    public void handleNotification(NotificationSendRequest request) {
        NotificationStrategy strategy = getStrategy(request.getNotificationType());

        notificationContext.setNotificationStrategy(strategy);
        notificationContext.send(request);
    }

    private NotificationStrategy getStrategy(NotificationType type) {
        return switch (type) {
            case EMAIL -> new EmailNotificationStrategy(emailService);
            case SMS -> new SmsNotificationStrategy();
            case PUSH -> new PushNotificationStrategy();
        };
    }

}
