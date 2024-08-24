package com.ftbootcamp.logservice.consumer;

import com.ftbootcamp.logservice.consumer.constants.KafkaTopicConstants;
import com.ftbootcamp.logservice.model.Log;
import com.ftbootcamp.logservice.model.documents.ExceptionLogMessage;
import com.ftbootcamp.logservice.model.documents.LogMessage;
import com.ftbootcamp.logservice.repository.ExceptionLogRepository;
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
    private final ExceptionLogRepository exceptionLogRepository;

    @KafkaListener(topics = KafkaTopicConstants.LOG_MESSAGE_TOPIC, groupId = "${kafka.log-message.group-id}")
    public void listenLogMessage(Log message) {

        if (message != null) {

            LogMessage logMessage = LogMessage.builder().
                    message(message.getMessage()).
                    createdDateTime(message.getCreatedDateTime()).
                    build();

            logRepository.save(logMessage);
        }
    }

    @KafkaListener(topics = KafkaTopicConstants.EXCEPTION_LOG_MESSAGE_TOPIC, groupId = "${kafka.log-message.group-id}")
    public void listenExceptionMessage(Log message) {

        if (message != null) {

            ExceptionLogMessage exceptionLogMessage = ExceptionLogMessage.builder().
                    message(message.getMessage()).
                    createdDateTime(message.getCreatedDateTime()).
                    build();

            exceptionLogRepository.save(exceptionLogMessage);
        }
    }
}
