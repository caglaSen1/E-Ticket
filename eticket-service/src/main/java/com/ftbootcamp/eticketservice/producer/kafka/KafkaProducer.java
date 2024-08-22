package com.ftbootcamp.eticketservice.producer.kafka;

import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.producer.entity.Log;
import com.ftbootcamp.eticketservice.producer.kafka.constants.KafkaTopicConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Object> indexKafkaTemplate;

    public void sendTrip(Trip trip) {
        log.info("trip sent to kafka: {}", trip.getId());
        indexKafkaTemplate.send(KafkaTopicConstants.TRIP_INDEX_TOPIC, trip);
    }

    public void sendLogMessage(Log message) {
        log.info("log message sent: {}", message);
        indexKafkaTemplate.send(KafkaTopicConstants.LOG_MESSAGE_TOPIC, message);
    }

    public void sendExceptionLogMessage(Log exceptionMessage) {
        log.info("log message sent: {}", exceptionMessage);
        indexKafkaTemplate.send(KafkaTopicConstants.EXCEPTION_LOG_MESSAGE_TOPIC, exceptionMessage);
    }
}