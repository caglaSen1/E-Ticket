package com.ftbootcamp.eticketsearchservice.dto.request;

import com.ftbootcamp.eticketsearchservice.enums.SortDirection;
import com.ftbootcamp.eticketsearchservice.enums.TripDocumentSortBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TripDocumentSearchRequest extends BaseSearchRequest{

    private TripDocumentSortBy sortBy;
    private SortDirection sortDirection;
    private Instant date;
    private String departureCity;
    private String arrivalCity;
    private String vehicleType;

}
