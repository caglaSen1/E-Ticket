package com.ftbootcamp.eticketsearchservice.service;

import com.ftbootcamp.eticketsearchservice.converter.TripConverter;
import com.ftbootcamp.eticketsearchservice.dto.request.TripDocumentSearchRequest;
import com.ftbootcamp.eticketsearchservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketsearchservice.model.constants.TripDocumentConstants;
import com.ftbootcamp.eticketsearchservice.model.document.TripDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<TripDocumentResponse> searchTrips(TripDocumentSearchRequest request) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // Filter by date
        if (request.getDate() != null) {
            LocalDate date = LocalDate.ofInstant(request.getDate(), ZoneOffset.UTC);
            Instant startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant endOfDay = startOfDay
                    .plus(Duration.ofHours(23))
                    .plus(Duration.ofMinutes(59))
                    .plus(Duration.ofSeconds(59));
            queryBuilder.must(QueryBuilders
                    .rangeQuery(TripDocumentConstants.DEPARTURE_TIME).gte(startOfDay).lte(endOfDay));
        }

        // Filter by departure city
        if (request.getDepartureCity() != null && !request.getDepartureCity().isEmpty()) {
            queryBuilder.must(QueryBuilders
                    .wildcardQuery(TripDocumentConstants.DEPARTURE_CITY,
                            "*" + request.getDepartureCity().toLowerCase() + "*"));
        }

        // Filter by arrival city
        if (request.getArrivalCity() != null && !request.getArrivalCity().isEmpty()) {
            queryBuilder.must(QueryBuilders
                    .wildcardQuery(TripDocumentConstants.ARRIVAL_CITY,
                            "*" + request.getArrivalCity().toLowerCase() + "*"));
        }

        // Filter by vehicle type
        if (request.getVehicleType() != null && !request.getVehicleType().isEmpty()) {
            queryBuilder.must(QueryBuilders
                    .wildcardQuery(TripDocumentConstants.VEHICLE_TYPE,
                            "*" + request.getVehicleType().toLowerCase() + "*"));
        }

        // Pagination information
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable);


        // Sorting
        if (request.getSortBy() != null) {
            switch (request.getSortBy()) {
                case DEPARTURE_TIME:
                    searchQueryBuilder.withSort(SortBuilders.fieldSort(TripDocumentConstants.DEPARTURE_TIME)
                            .order(SortOrder.fromString(request.getSortDirection().name())));
                    break;
                case ARRIVAL_TIME:
                    searchQueryBuilder.withSort(SortBuilders.fieldSort(TripDocumentConstants.ARRIVAL_TIME)
                            .order(SortOrder.fromString(request.getSortDirection().name())));
                    break;
                case PRICE:
                    searchQueryBuilder.withSort(SortBuilders.fieldSort(TripDocumentConstants.PRICE)
                            .order(SortOrder.fromString(request.getSortDirection().name())));
                    break;
            }
        }

        // Creating NativeSearchQuery
        NativeSearchQuery searchQuery = searchQueryBuilder.build();


        List<SearchHit<TripDocument>> searchHits = elasticsearchOperations.search(searchQuery, TripDocument.class,
                IndexCoordinates.of("trip")).getSearchHits();

        return TripConverter.toTripDocumentResponseList(searchHits.stream().map(SearchHit::getContent)
                .collect(Collectors.toList()));


    }
}
