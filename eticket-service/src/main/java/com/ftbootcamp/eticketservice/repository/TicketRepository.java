package com.ftbootcamp.eticketservice.repository;

import com.ftbootcamp.eticketservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.trip.id = :tripId")
    List<Ticket> findAllByTripId(Long tripId);

    @Query("SELECT t FROM Ticket t WHERE t.trip.id = :tripId AND t.isBought = false")
    List<Ticket> findNotSoldByTripId(Long tripId);

    @Modifying
    @Query("DELETE FROM Ticket t WHERE t.trip.id = :tripId")
    void deleteAllByTripId(Long tripId);
}
