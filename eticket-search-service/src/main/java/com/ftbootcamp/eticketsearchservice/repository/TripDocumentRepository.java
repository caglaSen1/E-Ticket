package com.ftbootcamp.eticketsearchservice.repository;

import com.ftbootcamp.eticketsearchservice.model.document.TripDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TripDocumentRepository extends ElasticsearchRepository<TripDocument, String> {
}
