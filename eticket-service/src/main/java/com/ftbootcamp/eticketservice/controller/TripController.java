package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.TripGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.dto.response.TripStatisticsResponse;
import com.ftbootcamp.eticketservice.entity.enums.TripCondition;
import com.ftbootcamp.eticketservice.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trip API V1", description = "Trip API for trip operations")
public class TripController {

    private final TripService tripService;

    @PostMapping("/admin-panel/add")
    @Operation(summary = "Add trip", description = "Add trip with given trip information. Only admin can add trip.")
    public GenericResponse<TripResponse> addTrip(@Valid @RequestBody TripCreateRequest request) {
        return GenericResponse.success(tripService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/admin-panel/{id}")
    @Operation(summary = "Get trip by id", description = "Get trip by id. Only admin can get trip by id.")
    public GenericResponse<TripResponse> getTripById(@PathVariable Long id) {
        return GenericResponse.success(tripService.getTripById(id), HttpStatus.OK);
    }

    @GetMapping("/all-available")
    @Operation(summary = "Get all available trips", description = "Get all available trips which are not expired, " +
            "not canceled and with at least one unsold ticket remaining. Company and Individual users can get all " +
            "available trips.")
    public GenericResponse<List<TripResponse>> getAllAvailableTrips() {
        return GenericResponse.success(tripService.getAllAvailableTrips(), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/all/by-condition")
    @Operation(summary = "Get all trips by condition", description = "Get all trips by condition which are; " +
            "available, expired but not canceled, canceled. Only admin can get all trips by condition.")
    public GenericResponse<List<TripResponse>> getAllTripsByCondition(@RequestParam TripCondition condition) {
        return GenericResponse.success(tripService.getAllTripsByCondition(condition), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/statistics")
    @Operation(summary = "Get general statistics", description = "Only admin can get general trip statistics.")
    public GenericResponse<TripGeneralStatisticsResponse> getGeneralTripStatistics() {
        return GenericResponse.success(tripService.getGeneralTripStatistics(), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/statistics/{id}")
    @Operation(summary = "Get trip statistics", description = "Only admin can get trip statistics.")
    public GenericResponse<TripStatisticsResponse> getTripStatistics(@PathVariable Long id) {
        return GenericResponse.success(tripService.getTripStatistics(id), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/update")
    @Operation(summary = "Update trip", description = "Only admin can update trip.")
    public GenericResponse<TripResponse> updateTrip(@Valid @RequestBody TripUpdateRequest request) {
        return GenericResponse.success(tripService.updateTrip(request), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/cancel/{id}")
    @Operation(summary = "Cancel trip", description = "Only admin can cancel trip.")
    public GenericResponse<String> cancelTrip(@PathVariable Long id) {
        tripService.cancelTrip(id);
        return GenericResponse.success(null, HttpStatus.OK);
    }
}
