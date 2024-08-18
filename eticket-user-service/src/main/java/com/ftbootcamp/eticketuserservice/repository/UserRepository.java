package com.ftbootcamp.eticketuserservice.repository;

import com.ftbootcamp.eticketuserservice.entity.User;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    long count();

    @Query("SELECT u FROM User u WHERE u.statusType IN :statusList")
    List<User> findByStatusList(List<StatusType> statusList);

    @Query("SELECT u FROM User u WHERE u.userType IN :userTypeList")
    List<User> findByTypeList(List<UserType> userTypeList);

    @Query("SELECT u FROM User u WHERE u.email IN :emailList")
    List<User> findByEmailList(List<String> emailList);
}
