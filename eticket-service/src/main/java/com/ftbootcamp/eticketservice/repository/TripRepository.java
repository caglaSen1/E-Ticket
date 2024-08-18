package com.ftbootcamp.eticketservice.repository;

import com.ftbootcamp.eticketservice.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {

}
