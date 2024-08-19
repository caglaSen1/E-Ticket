package com.ftbootcamp.emailservice.consumer;

import com.ftbootcamp.emailservice.dto.request.EmailSendRequest;
import com.ftbootcamp.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.send.email.queue}")
    public void sendEmail(EmailSendRequest request){

        log.info("Email message: {}", request.toString());

        emailService.sendEmail(request);
    }

}
