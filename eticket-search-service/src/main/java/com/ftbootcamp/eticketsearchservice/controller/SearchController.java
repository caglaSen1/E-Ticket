package com.ftbootcamp.eticketsearchservice.controller;

import com.ftbootcamp.eticketsearchservice.dto.request.TripDocumentSearchRequest;
import com.ftbootcamp.eticketsearchservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketsearchservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketsearchservice.dto.response.TripSearchResponse;
import com.ftbootcamp.eticketsearchservice.enums.SortDirection;
import com.ftbootcamp.eticketsearchservice.enums.TripDocumentSortBy;
import com.ftbootcamp.eticketsearchservice.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@Tag(name = "Search API V1", description = "Search API for trips")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/trips")
    @Operation(summary = "Search trips", description = "Search trips with given search criteria, for all users")
    public GenericResponse<List<TripSearchResponse>> searchTrips(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) TripDocumentSortBy sortBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        TripDocumentSearchRequest request = new TripDocumentSearchRequest();
        request.setDate(date != null ? date.atStartOfDay().toInstant(ZoneOffset.UTC) : null);
        request.setDepartureCity(departureCity);
        request.setArrivalCity(arrivalCity);
        request.setVehicleType(vehicleType);
        request.setSortBy(sortBy);
        request.setSortDirection(sortDirection);
        request.setPage(page);
        request.setSize(size);

        return GenericResponse.success(searchService.searchTrips(request), HttpStatus.OK);
    }

    @GetMapping("/trips-for-admin")
    @Operation(summary = "Search trips for admin", description = "Search trips with given search criteria, " +
            "authorized for admin users only")
    public GenericResponse<List<TripSearchResponse>> searchTripsForAdmin(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) TripDocumentSortBy sortBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        TripDocumentSearchRequest request = new TripDocumentSearchRequest();
        request.setDate(date != null ? date.atStartOfDay().toInstant(ZoneOffset.UTC) : null);
        request.setDepartureCity(departureCity);
        request.setArrivalCity(arrivalCity);
        request.setVehicleType(vehicleType);
        request.setSortBy(sortBy);
        request.setSortDirection(sortDirection);
        request.setPage(page);
        request.setSize(size);

        return GenericResponse.success(searchService.searchTripsForAdmin(request), HttpStatus.OK);
    }
}
