package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.UserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.UserBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;

    // TODO: add update method, viewPersonalInfo() - profile, updatePersonalInfo

    public List<UserSummaryResponse> getAllUsers() {
        return UserConverter.toUserSummaryResponse(userRepository.findAll());
    }

    public UserDetailsResponse getUserById(Long id) {
        return UserConverter.toUserDetailsResponse(userBusinessRules.checkUserExistById(id));
    }

    public UserDetailsResponse getUserByEmail(String email) {
        return UserConverter.toUserDetailsResponse(userBusinessRules.checkUserExistByEmail(email));
    }

    public int getHowManyUsers() {
        return (int) userRepository.count();
    }

}
