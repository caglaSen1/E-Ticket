package com.ftbootcamp.eticketindexservice.service;

import com.ftbootcamp.eticketindexservice.converter.TripConverter;
import com.ftbootcamp.eticketindexservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketindexservice.model.TripDocument;
import com.ftbootcamp.eticketindexservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexService {

    private final TripRepository tripRepository;

    public List<TripDocumentResponse> getAllTripDocumentResponses() {
        Iterable<TripDocument> tripDocuments = tripRepository.findAll();

        return TripConverter.toTripDocumentResponseList(StreamSupport.stream(tripDocuments.spliterator(), false)
                .collect(Collectors.toList()));
    }
}
