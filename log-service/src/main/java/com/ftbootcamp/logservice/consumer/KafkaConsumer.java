package com.ftbootcamp.logservice.consumer;

import com.ftbootcamp.logservice.consumer.constants.KafkaTopicConstants;
import com.ftbootcamp.logservice.entity.LogMessage;
import com.ftbootcamp.logservice.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final LogRepository logRepository;

    @KafkaListener(topics = KafkaTopicConstants.LOG_MESSAGE_TOPIC, groupId = "${kafka.log-message.group-id}")
    public void listen(String logMessage) {

        if (logMessage != null && !logMessage.isEmpty()) {
            log.info("Received message: {}", logMessage);

            LogMessage message = new LogMessage(logMessage);
            logRepository.save(message);
        }

    }

}
