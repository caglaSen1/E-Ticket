package com.ftbootcamp.notificationservice.consumer;

import com.ftbootcamp.notificationservice.consumer.strategy.*;
import com.ftbootcamp.notificationservice.repository.PushNotificationRepository;
import com.ftbootcamp.notificationservice.repository.SmsRepository;
import com.ftbootcamp.notificationservice.service.EmailService;
import com.ftbootcamp.notificationservice.consumer.enums.NotificationType;
import com.ftbootcamp.notificationservice.consumer.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final EmailService emailService;
    private final SmsRepository smsRepository;
    private final PushNotificationRepository pushNotificationRepository;
    private final NotificationContext notificationContext;

    @RabbitListener(queuesToDeclare = @Queue("${rabbitmq.send.notification.queue}"))
    public void handleNotification(NotificationSendRequest request) {
        List<NotificationType> notificationTypes = request.getNotificationTypes();

        for(NotificationType type : notificationTypes) {
            notificationContext.setNotificationStrategy(getStrategy(type));
            notificationContext.send(request);
        }
    }

    private NotificationStrategy getStrategy(NotificationType type) {
        return switch (type) {
            case EMAIL -> new EmailNotificationStrategy(emailService);
            case SMS -> new SmsNotificationStrategy(smsRepository);
            case PUSH -> new PushNotificationStrategy(pushNotificationRepository);
        };
    }
}
