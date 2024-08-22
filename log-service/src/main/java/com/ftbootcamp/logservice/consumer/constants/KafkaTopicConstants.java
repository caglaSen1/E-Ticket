package com.ftbootcamp.logservice.consumer.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {

    public static final String LOG_MESSAGE_TOPIC = "message_log_topic";
}
