package com.ftbootcamp.eticketindexservice.repository;

import com.ftbootcamp.eticketindexservice.model.TripDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TripDocumentRepository extends ElasticsearchRepository<TripDocument, String> {
}
