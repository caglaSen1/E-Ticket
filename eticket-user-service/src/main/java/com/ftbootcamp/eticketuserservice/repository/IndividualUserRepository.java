package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {
    Optional<IndividualUser> findByEmail(String email);

    @Query("SELECT u FROM IndividualUser u WHERE u.statusType IN :statusList")
    Page<IndividualUser> findByStatusList(List<StatusType> statusList, Pageable pageable);

    @Query("SELECT u FROM IndividualUser u WHERE u.userType IN :userTypeList")
    Page<IndividualUser> findByTypeList(List<UserType> userTypeList, Pageable pageable);

    @Query("SELECT u FROM IndividualUser u WHERE u.email IN :emailList")
    Page<IndividualUser> findByEmailList(List<String> emailList, Pageable pageable);
}

