package com.ftbootcamp.eticketservice.producer.kafka.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {

    public static final String LOG_MESSAGE_TOPIC = "log_message_topic";
    public static final String ERROR_LOG_MESSAGE_TOPIC = "error_log_message_topic";
    public static final String TRIP_INDEX_TOPIC = "trip_index_topic";
}
