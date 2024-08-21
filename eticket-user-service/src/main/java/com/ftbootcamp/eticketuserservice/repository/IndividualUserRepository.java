package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {
    Optional<IndividualUser> findByEmail(String email);

    @Query("SELECT u FROM IndividualUser u WHERE u.statusType IN :statusList")
    List<IndividualUser> findByStatusList(List<StatusType> statusList);

    @Query("SELECT u FROM IndividualUser u WHERE u.userType IN :userTypeList")
    List<IndividualUser> findByTypeList(List<UserType> userTypeList);

    @Query("SELECT u FROM IndividualUser u WHERE u.email IN :emailList")
    List<IndividualUser> findByEmailList(List<String> emailList);
}

