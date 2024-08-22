package com.ftbootcamp.notificationservice.producer.kafka.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {

    public static final String LOG_MESSAGE_TOPIC = "log_message_topic";
    public static final String EXCEPTION_LOG_MESSAGE_TOPIC = "exception_log_message_topic";
}
