package com.ftbootcamp.eticketindexservice.consumer;

import com.ftbootcamp.eticketindexservice.consumer.constants.KafkaTopicConstants;
import com.ftbootcamp.eticketindexservice.converter.TripConverter;
import com.ftbootcamp.eticketindexservice.model.Trip;
import com.ftbootcamp.eticketindexservice.repository.TripDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TripDocumentRepository tripDocumentRepository;

    @KafkaListener(topics = KafkaTopicConstants.TRIP_INDEX_TOPIC, groupId = "${kafka.trip.group-id}")
    public void listenTrip(Trip trip) {

        log.info("Trip recieved from Kafka: {}", trip);

        tripDocumentRepository.save(TripConverter.toTripDocument(trip));

    }

}
