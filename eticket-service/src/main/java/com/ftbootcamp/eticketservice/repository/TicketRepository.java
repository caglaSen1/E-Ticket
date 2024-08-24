package com.ftbootcamp.eticketservice.repository;

import com.ftbootcamp.eticketservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.isDeleted = false")
    List<Ticket> findAll();

    @Query("SELECT t FROM Ticket t WHERE t.id = :id AND t.isDeleted = false")
    Optional<Ticket> findById(long id);

    @Query("SELECT t FROM Ticket t WHERE t.trip.id = :tripId AND t.isDeleted = false")
    List<Ticket> findAllByTripId(Long tripId);

    @Query("SELECT t FROM Ticket t WHERE t.trip.id = :tripId AND t.isDeleted = false AND " +
            "t.isSold = false AND " +
            "t.trip.arrivalTime > CURRENT_TIMESTAMP")
    List<Ticket> findAllAvailableTicketsByTripId(Long tripId);

    @Query("SELECT t FROM Ticket t WHERE t.isDeleted = false AND " +
            "t.isSold = false AND " +
            "t.trip.arrivalTime > CURRENT_TIMESTAMP")
    List<Ticket> findAllAvailableTickets();

    @Query("SELECT t FROM Ticket t WHERE t.isDeleted = false AND " +
            "t.trip.arrivalTime < CURRENT_TIMESTAMP")
    List<Ticket> findAllExpiredTickets();

    @Query("SELECT t FROM Ticket t WHERE t.isDeleted = false AND " +
            "t.isSold = true")
    List<Ticket> findAllSoldTickets();

    @Query("SELECT t FROM Ticket t WHERE t.passengerEmail = :email AND t.isDeleted = false")
    List<Ticket> findAllByUserEmail(String email);
}
