package com.ftbootcamp.eticketsearchservice.helper;

import com.ftbootcamp.eticketsearchservice.dto.request.BaseSearchRequest;
import com.ftbootcamp.eticketsearchservice.dto.request.TripDocumentSearchRequest;
import com.ftbootcamp.eticketsearchservice.model.constants.TripDocumentConstants;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class FilterHelper {

    public static BoolQueryBuilder buildSearchQuery(BaseSearchRequest request) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (request instanceof TripDocumentSearchRequest tripRequest) {

            // Filter by date
            if (tripRequest.getDate() != null) {
                LocalDate date = LocalDate.ofInstant(tripRequest.getDate(), ZoneOffset.UTC);
                Instant startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
                Instant endOfDay = startOfDay
                        .plus(Duration.ofHours(23))
                        .plus(Duration.ofMinutes(59))
                        .plus(Duration.ofSeconds(59));
                queryBuilder.must(QueryBuilders
                        .rangeQuery(TripDocumentConstants.DEPARTURE_TIME).gte(startOfDay).lte(endOfDay));
            }

            // Filter by departure city
            if (tripRequest.getDepartureCity() != null && !tripRequest.getDepartureCity().isEmpty()) {
                queryBuilder.must(QueryBuilders
                        .wildcardQuery(TripDocumentConstants.DEPARTURE_CITY,
                                "*" + tripRequest.getDepartureCity().toLowerCase() + "*"));
            }

            // Filter by arrival city
            if (tripRequest.getArrivalCity() != null && !tripRequest.getArrivalCity().isEmpty()) {
                queryBuilder.must(QueryBuilders
                        .wildcardQuery(TripDocumentConstants.ARRIVAL_CITY,
                                "*" + tripRequest.getArrivalCity().toLowerCase() + "*"));
            }

            // Filter by vehicle type
            if (tripRequest.getVehicleType() != null && !tripRequest.getVehicleType().isEmpty()) {
                queryBuilder.must(QueryBuilders
                        .wildcardQuery(TripDocumentConstants.VEHICLE_TYPE,
                                "*" + tripRequest.getVehicleType().toLowerCase() + "*"));
            }
        }

        return queryBuilder;
    }
}
