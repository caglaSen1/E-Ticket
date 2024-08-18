package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trip API V1", description = "Trip API for trip operations")
public class TripController {

    private final TripService tripService;

    @PostMapping()
    @Operation(summary = "Add trip", description = "Add trip with given trip information")
    public GenericResponse<TripResponse> addTrip(@RequestBody TripCreateRequest request) {
        return GenericResponse.success(tripService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get trip by id", description = "Get trip by id")
    public GenericResponse<TripResponse> getTripById(@PathVariable Long id) {
        return GenericResponse.success(tripService.getTripById(id), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update trip", description = "Update trip with given trip information")
    public GenericResponse<TripResponse> updateTrip(@RequestBody TripUpdateRequest request) {
        return GenericResponse.success(tripService.updateTrip(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete trip", description = "Delete trip by id")
    public GenericResponse<String> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return GenericResponse.success(null, HttpStatus.OK);
    }
}
