package com.ftbootcamp.eticketauthservice.repository;

import com.ftbootcamp.eticketauthservice.entity.concrete.IndividualUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {

}

