package com.ftbootcamp.eticketservice.service;

import com.ftbootcamp.eticketservice.dto.request.TripCreateRequest;
import com.ftbootcamp.eticketservice.dto.request.TripUpdateRequest;
import com.ftbootcamp.eticketservice.dto.response.TripGeneralStatisticsResponse;
import com.ftbootcamp.eticketservice.dto.response.TripResponse;
import com.ftbootcamp.eticketservice.dto.response.TripStatisticsResponse;
import com.ftbootcamp.eticketservice.entity.Trip;
import com.ftbootcamp.eticketservice.entity.constant.TripEntityConstants;
import com.ftbootcamp.eticketservice.producer.Log;
import com.ftbootcamp.eticketservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketservice.repository.TripRepository;
import com.ftbootcamp.eticketservice.rules.TripBusinessRules;
import com.ftbootcamp.eticketservice.entity.enums.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.ftbootcamp.eticketservice.entity.enums.VehicleType.BUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripBusinessRules tripBusinessRules;

    @Mock
    private TicketService ticketService;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrip() {
        // given
        TripCreateRequest request = new TripCreateRequest();
        request.setDepartureTime(LocalDateTime.parse("2024-08-23T10:00:00"));
        request.setArrivalTime(LocalDateTime.parse("2024-08-23T14:00:00"));
        request.setPrice(100.0);
        request.setVehicleType(BUS);

        Trip trip = new Trip();
        trip.setId(1L);

        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        // when
        TripResponse response = tripService.create(request);

        // then
        verify(tripBusinessRules, times(1)).checkArrivalTimeValid(request.getDepartureTime(), request.getArrivalTime());
        verify(tripBusinessRules, times(1)).checkPriceValid(request.getPrice());
        verify(tripRepository, times(1)).save(any(Trip.class));
        verify(ticketService, times(1)).generateTicketsForTrip(any(Trip.class));
        verify(kafkaProducer, times(1)).sendTrip(any(Trip.class));
        verify(kafkaProducer, times(1)).sendLogMessage(any(Log.class));

        assertEquals(trip.getId(), response.getId());
    }

    @Test
    void testGetTripById() {
        // given
        Long tripId = 1L;
        Trip trip = new Trip();
        trip.setId(tripId);

        when(tripBusinessRules.checkTripExistById(tripId)).thenReturn(trip);

        // when
        TripResponse response = tripService.getTripById(tripId);

        // then
        assertEquals(tripId, response.getId());
        verify(tripBusinessRules, times(1)).checkTripExistById(tripId);
    }

    @Test
    void testGetAllAvailableTrips() {
        // given
        List<Trip> trips = Arrays.asList(new Trip(), new Trip());
        when(tripRepository.findAllAvailableTrips()).thenReturn(trips);

        // when
        List<TripResponse> responses = tripService.getAllAvailableTrips();

        // then
        assertEquals(trips.size(), responses.size());
        verify(tripRepository, times(1)).findAllAvailableTrips();
    }

    @Test
    void testGetAllExpiredNotCanceledTrips() {
        // given
        List<Trip> trips = Arrays.asList(new Trip(), new Trip());
        when(tripRepository.findExpiredTrips()).thenReturn(trips);

        // when
        List<TripResponse> responses = tripService.getAllExpiredNotCanceledTrips();

        // then
        assertEquals(trips.size(), responses.size());
        verify(tripRepository, times(1)).findExpiredTrips();
    }

    @Test
    void testGetAllCancelledTrips() {
        // given
        List<Trip> trips = Arrays.asList(new Trip(), new Trip());
        when(tripRepository.findCancelledTrips()).thenReturn(trips);

        // when
        List<TripResponse> responses = tripService.getAllCancelledTrips();

        // then
        assertEquals(trips.size(), responses.size());
        verify(tripRepository, times(1)).findCancelledTrips();
    }

    @Test
    void testGetGeneralTripStatistics() {
        // given
        List<Trip> allTrips = Arrays.asList(new Trip(), new Trip(), new Trip());
        when(tripRepository.findAll()).thenReturn(allTrips);
        when(tripRepository.findAllAvailableTrips()).thenReturn(Arrays.asList(new Trip(), new Trip()));
        when(tripRepository.findExpiredTrips()).thenReturn(Arrays.asList(new Trip()));
        when(tripRepository.findCancelledTrips()).thenReturn(Arrays.asList(new Trip()));

        // when
        TripGeneralStatisticsResponse response = tripService.getGeneralTripStatistics();

        // then
        assertEquals(allTrips.size(), response.getTotalTripCount());
        assertEquals(2, response.getTotalAvailableTripCount());
        assertEquals(1, response.getTotalExpiredNotCanceledTripCount());
        assertEquals(1, response.getTotalCancelledTripCount());

        verify(tripRepository, times(1)).findAll();
        verify(tripRepository, times(1)).findAllAvailableTrips();
        verify(tripRepository, times(1)).findExpiredTrips();
        verify(tripRepository, times(1)).findCancelledTrips();
    }

    @Test
    void testGetTripStatistics() {
        // given
        Long tripId = 1L;
        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setTotalTicketCount(100);
        trip.setSoldTicketCount(50);
        trip.setPrice(200.0);

        when(tripBusinessRules.checkTripExistById(tripId)).thenReturn(trip);

        // when
        TripStatisticsResponse response = tripService.getTripStatistics(tripId);

        // then
        assertEquals(100, response.getTotalTicketCount());
        assertEquals(50, response.getSoldTicketCount());
        assertEquals(10000.0, response.getTotalSoldTicketPrice());

        verify(tripBusinessRules, times(1)).checkTripExistById(tripId);
    }

    @Test
    void testUpdateTrip() {
        // given
        TripUpdateRequest request = new TripUpdateRequest();
        request.setId(1L);
        request.setDepartureTime(LocalDateTime.parse("2024-08-23T10:00:00"));
        request.setArrivalTime(LocalDateTime.parse("2024-08-23T14:00:00"));
        request.setTotalTicketCount(100);
        request.setPrice(200.0);

        Trip trip = new Trip();
        trip.setId(1L);

        when(tripBusinessRules.checkTripExistById(request.getId())).thenReturn(trip);
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        // when
        TripResponse response = tripService.updateTrip(request);

        // then
        verify(tripBusinessRules, times(1)).checkIfThereAreAnySoldTickets(request.getId());
        verify(tripBusinessRules, times(1)).checkArrivalTimeValid(request.getDepartureTime(), request.getArrivalTime());
        verify(tripBusinessRules, times(1)).checkTotalTicketCountValid(request.getTotalTicketCount());
        verify(tripBusinessRules, times(1)).checkPriceValid(request.getPrice());
        verify(tripRepository, times(1)).save(any(Trip.class));
        verify(kafkaProducer, times(1)).sendTrip(any(Trip.class));
        verify(kafkaProducer, times(1)).sendLogMessage(any(Log.class));

        assertEquals(trip.getId(), response.getId());
    }

    @Test
    void testCancelTrip() {
        // given
        Long tripId = 1L;
        Trip trip = new Trip();
        trip.setId(tripId);

        when(tripBusinessRules.checkTripExistById(tripId)).thenReturn(trip);

        // when
        tripService.cancelTrip(tripId);

        // then
        assertEquals(true, trip.isCancelled());
        verify(tripBusinessRules, times(1)).checkIfThereAreAnySoldTickets(tripId);
        verify(ticketService, times(1)).deleteTicketsByTripId(tripId);
        verify(tripRepository, times(1)).save(trip);
        verify(kafkaProducer, times(1)).sendTrip(trip);
        verify(kafkaProducer, times(1)).sendLogMessage(any(Log.class));
    }
}
