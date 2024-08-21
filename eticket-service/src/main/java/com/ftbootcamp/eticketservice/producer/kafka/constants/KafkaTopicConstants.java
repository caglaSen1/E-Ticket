package com.ftbootcamp.eticketservice.producer.kafka.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {

    public static final String LOG_TOPIC = "message_log_topic";
    public static final String TRIP_INDEX_TOPIC = "trip_index_topic";
}
