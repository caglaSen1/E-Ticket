package com.ftbootcamp.eticketauthservice.repository;

import com.ftbootcamp.eticketauthservice.entity.concrete.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    Optional<CompanyUser> findByEmail(String email);
}

