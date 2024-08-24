package com.ftbootcamp.eticketservice.controller;

import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponseConstants;
import com.ftbootcamp.eticketservice.dto.response.TripGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.dto.response.TripStatisticsResponse;
import com.ftbootcamp.eticketservice.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripControllerTest {

    @Mock
    private TripService tripService;

    @InjectMocks
    private TripController tripController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTrip() {
        // given
        TripCreateRequest request = new TripCreateRequest();
        TripResponse response = new TripResponse();
        when(tripService.create(any(TripCreateRequest.class))).thenReturn(response);

        // when
        GenericResponse<TripResponse> result = tripController.addTrip(request);

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.CREATED, result.getHttpStatus());
        assertEquals(response, result.getData());
        verify(tripService, times(1)).create(request);
    }

    @Test
    void testGetTripById() {
        // given
        Long tripId = 1L;
        TripResponse response = new TripResponse();
        when(tripService.getTripById(tripId)).thenReturn(response);

        // when
        GenericResponse<TripResponse> result = tripController.getTripById(tripId);

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(response, result.getData());
        verify(tripService, times(1)).getTripById(tripId);
    }

    @Test
    void testGetAllAvailableTrips() {
        // given
        List<TripResponse> responseList = Arrays.asList(new TripResponse(), new TripResponse());
        when(tripService.getAllAvailableTrips()).thenReturn(responseList);

        // when
        GenericResponse<List<TripResponse>> result = tripController.getAllAvailableTrips();

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(responseList, result.getData());
        verify(tripService, times(1)).getAllAvailableTrips();
    }

    @Test
    void testGetAllExpiredTrips() {
        // given
        List<TripResponse> responseList = Arrays.asList(new TripResponse(), new TripResponse());
        when(tripService.getAllExpiredNotCanceledTrips()).thenReturn(responseList);

        // when
        GenericResponse<List<TripResponse>> result = tripController.getAllExpiredTrips();

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(responseList, result.getData());
        verify(tripService, times(1)).getAllExpiredNotCanceledTrips();
    }

    @Test
    void testGetAllCancelledTrips() {
        // given
        List<TripResponse> responseList = Arrays.asList(new TripResponse(), new TripResponse());
        when(tripService.getAllCancelledTrips()).thenReturn(responseList);

        // when
        GenericResponse<List<TripResponse>> result = tripController.getAllCancelledTrips();

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(responseList, result.getData());
        verify(tripService, times(1)).getAllCancelledTrips();
    }

    @Test
    void testGetGeneralTripStatistics() {
        // given
        TripGeneralStatisticsResponse response = new TripGeneralStatisticsResponse();
        when(tripService.getGeneralTripStatistics()).thenReturn(response);

        // when
        GenericResponse<TripGeneralStatisticsResponse> result = tripController.getGeneralTripStatistics();

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(response, result.getData());
        verify(tripService, times(1)).getGeneralTripStatistics();
    }

    @Test
    void testGetTripStatistics() {
        // given
        Long tripId = 1L;
        TripStatisticsResponse response = new TripStatisticsResponse();
        when(tripService.getTripStatistics(tripId)).thenReturn(response);

        // when
        GenericResponse<TripStatisticsResponse> result = tripController.getTripStatistics(tripId);

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(response, result.getData());
        verify(tripService, times(1)).getTripStatistics(tripId);
    }

    @Test
    void testUpdateTrip() {
        // given
        TripUpdateRequest request = new TripUpdateRequest();
        TripResponse response = new TripResponse();
        when(tripService.updateTrip(any(TripUpdateRequest.class))).thenReturn(response);

        // when
        GenericResponse<TripResponse> result = tripController.updateTrip(request);

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(response, result.getData());
        verify(tripService, times(1)).updateTrip(request);
    }

    @Test
    void testCancelTrip() {
        // given
        Long tripId = 1L;

        // when
        GenericResponse<String> result = tripController.cancelTrip(tripId);

        // then
        assertEquals(GenericResponseConstants.SUCCESS, result.getStatus());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals(null, result.getData());
        verify(tripService, times(1)).cancelTrip(tripId);
    }
}
