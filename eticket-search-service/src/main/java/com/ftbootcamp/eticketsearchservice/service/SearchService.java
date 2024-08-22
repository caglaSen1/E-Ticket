package com.ftbootcamp.eticketsearchservice.service;

import com.ftbootcamp.eticketsearchservice.converter.TripConverter;
import com.ftbootcamp.eticketsearchservice.dto.request.BaseSearchRequest;
import com.ftbootcamp.eticketsearchservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketsearchservice.dto.response.TripSearchResponse;
import com.ftbootcamp.eticketsearchservice.helper.FilterHelper;
import com.ftbootcamp.eticketsearchservice.helper.SortHelper;
import com.ftbootcamp.eticketsearchservice.model.constants.TripDocumentConstants;
import com.ftbootcamp.eticketsearchservice.model.document.TripDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<TripSearchResponse> searchTrips(BaseSearchRequest request) {

        // Filtering
        BoolQueryBuilder queryBuilder = FilterHelper.buildSearchQuery(request);

        // Available trips for all users
        queryBuilder.must(QueryBuilders.termQuery(TripDocumentConstants.IS_CANCELLED, false));
        queryBuilder.must(QueryBuilders.scriptQuery(new Script(
                ScriptType.INLINE,
                "painless",
                "doc['" + TripDocumentConstants.SOLD_TICKET_COUNT + "'].value < doc['" +
                        TripDocumentConstants.TOTAL_TICKET_COUNT + "'].value",
                Collections.emptyMap()
        )));
        queryBuilder.must(QueryBuilders.rangeQuery(TripDocumentConstants.ARRIVAL_TIME)
                .gt("now"));

        // Pagination
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable);

        // Sorting
        SortHelper.applySorting(searchQueryBuilder, request);

        // Creating NativeSearchQuery
        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        List<SearchHit<TripDocument>> searchHits = elasticsearchOperations.search(searchQuery, TripDocument.class,
                IndexCoordinates.of("trip")).getSearchHits();

        List<TripDocument> tripDocuments = searchHits.stream().map(SearchHit::getContent)
                .collect(Collectors.toList());

        // Convert List to Page
        Page<TripDocument> tripDocumentsPage = new PageImpl<>(tripDocuments, pageable, tripDocuments.size());

        // Wrap the TripSearchResponse in a List
        List<TripSearchResponse> tripSearchResponses = new ArrayList<>();
        tripSearchResponses.add(TripConverter.toTripSearchResponse(tripDocumentsPage));

        return tripSearchResponses;
    }

    public List<TripSearchResponse> searchTripsForAdmin(BaseSearchRequest request) {

        // Filtering
        BoolQueryBuilder queryBuilder = FilterHelper.buildSearchQuery(request);

        // Pagination
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable);

        // Sorting
        SortHelper.applySorting(searchQueryBuilder, request);

        // Creating NativeSearchQuery
        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        List<SearchHit<TripDocument>> searchHits = elasticsearchOperations.search(searchQuery, TripDocument.class,
                IndexCoordinates.of("trip")).getSearchHits();

        List<TripDocument> tripDocuments = searchHits.stream().map(SearchHit::getContent)
                .collect(Collectors.toList());

        // Convert List to Page
        Page<TripDocument> tripDocumentsPage = new PageImpl<>(tripDocuments, pageable, tripDocuments.size());

        // Wrap the TripSearchResponse in a List
        List<TripSearchResponse> tripSearchResponses = new ArrayList<>();
        tripSearchResponses.add(TripConverter.toTripSearchResponse(tripDocumentsPage));

        return tripSearchResponses;
    }
}
