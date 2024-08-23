package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH IndividualUser i ON u.id = i.id "
            + "LEFT JOIN FETCH AdminUser a ON u.id = a.id "
            + "LEFT JOIN FETCH CompanyUser c ON u.id = c.id "
            + "WHERE u.id = :id")
    Optional<User> findUserWithDetailsById(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH IndividualUser i ON u.id = i.id "
            + "LEFT JOIN FETCH AdminUser a ON u.id = a.id "
            + "LEFT JOIN FETCH CompanyUser c ON u.id = c.id "
            + "WHERE u.email = :email")
    Optional<User> findUserWithDetailsByEmail(@Param("email") String email);

}
