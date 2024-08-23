package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    Optional<CompanyUser> findByEmail(String email);

    @Query("SELECT u FROM CompanyUser u WHERE u.statusType IN :statusList")
    Page<CompanyUser> findByStatusList(List<StatusType> statusList, Pageable pageable);

    @Query("SELECT u FROM CompanyUser u WHERE u.userType IN :userTypeList")
    Page<CompanyUser> findByTypeList(List<UserType> userTypeList, Pageable pageable);

    @Query("SELECT u FROM CompanyUser u WHERE u.email IN :emailList")
    Page<CompanyUser> findByEmailList(List<String> emailList, Pageable pageable);

}

