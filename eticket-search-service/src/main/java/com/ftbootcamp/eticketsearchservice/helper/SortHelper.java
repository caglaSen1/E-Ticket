package com.ftbootcamp.eticketsearchservice.helper;

import com.ftbootcamp.eticketsearchservice.dto.request.BaseSearchRequest;
import com.ftbootcamp.eticketsearchservice.dto.request.TripDocumentSearchRequest;
import com.ftbootcamp.eticketsearchservice.model.constants.TripDocumentConstants;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

public class SortHelper {

    public static void applySorting(NativeSearchQueryBuilder searchQueryBuilder, BaseSearchRequest request) {

        if (request instanceof TripDocumentSearchRequest tripRequest) {
            if (tripRequest.getSortBy() != null) {
                switch (tripRequest.getSortBy()) {
                    case PRICE:
                        searchQueryBuilder.withSort(SortBuilders.fieldSort(TripDocumentConstants.PRICE)
                                .order(SortOrder.fromString(request.getSortDirection().name())));
                        break;
                }
            }
        }
    }
}
