package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    Optional<CompanyUser> findByEmail(String email);

    @Query("SELECT u FROM CompanyUser u WHERE u.statusType IN :statusList")
    List<CompanyUser> findByStatusList(List<StatusType> statusList);

    @Query("SELECT u FROM CompanyUser u WHERE u.userType IN :userTypeList")
    List<CompanyUser> findByTypeList(List<UserType> userTypeList);

    @Query("SELECT u FROM CompanyUser u WHERE u.email IN :emailList")
    List<CompanyUser> findByEmailList(List<String> emailList);

}

