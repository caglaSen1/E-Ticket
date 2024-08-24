package com.ftbootcamp.eticketservice.repository;

import com.ftbootcamp.eticketservice.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE t.isCancelled = false AND t.soldTicketCount < t.totalTicketCount AND " +
            "t.arrivalTime > CURRENT_TIMESTAMP")
    List<Trip> findAllAvailableTrips();

    @Query("SELECT t FROM Trip t WHERE t.isCancelled = false AND t.arrivalTime < CURRENT_TIMESTAMP")
    List<Trip> findExpiredTrips();

    @Query("SELECT t FROM Trip t WHERE t.isCancelled = true")
    List<Trip> findCancelledTrips();
}
